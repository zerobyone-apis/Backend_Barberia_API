package zero.our.piece.barbers.barbers_api.reserve.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.enterprise.service.EnterpriseService
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.magicCube.mailing.SendMailService
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveRequestDTO
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveResponseDTO
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves
import zero.our.piece.barbers.barbers_api.reserve.repository.ReserveRepository
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesRequestDTO
import zero.our.piece.barbers.barbers_api.services.model.WorkServices
import zero.our.piece.barbers.barbers_api.services.service.WorkServiceProvidesService
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.RegisterLoginService
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.sql.Time
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
@Slf4j
class ReserveServices {

    @Autowired
    ReserveRepository reserveRepository

    @Autowired
    UserService userService

    @Autowired
    EnterpriseService enterpriseService

    @Autowired
    ClientService clientService

    @Autowired
    WorkServiceProvidesService workService

    @Autowired
    RegisterLoginService registerLoginService

    @Autowired
    SendMailService sendMailService

    List<ReserveResponseDTO> findAll() {
        try {
            reserveRepository.findAll().collect { it -> decoratorPatternReserve(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllActives() {
        try {
            reserveRepository.findAll().stream().filter { it.isActive }.collect { it -> decoratorPatternReserve(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findById(Long reserveId) {
        try {
            def foundReserve = reserveRepository.findById(reserveId).get()
            if (!foundReserve?.id) throw new ResourceNotFoundException("RESERVE_NOT_FOUND")

            return decoratorPatternReserve(foundReserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findByUserIdAndDate(Long userId, String date) {
        try {

            Reserves reserve = reserveRepository.findByUserIdAndData(userId, date)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ${userId} for this Date ${date} Not found")

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findByClientId(Long clientId) {
        try {

            Reserves reserve = reserveRepository.findByClientId(clientId, Instant.now())
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: " + clientId)

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllByUserId(Long userId) {
        try {

            List<Reserves> reserve = reserveRepository.findAllByUserId(userId)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: ${userId}")

            return reserve.collect { it -> decoratorPatternReserve(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllActivesByUserId(Long userId) {
        try {

            List<Reserves> reserve = reserveRepository.findAllActivesByUserId(userId)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: ${userId}")

            return reserve.collect { it -> decoratorPatternReserve(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllByClientId(Long clientId) {
        try {

            List<Reserves> reserve = reserveRepository.findAllByClientId(clientId)
            if (!reserve?.id) throw new ResourceNotFoundException("Reserve with this Client ID Not found: ${clientId}")

            return reserve.collect { it -> decoratorPatternReserve(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    void cancel(Long id, Long userId) {
        try {
            User user = userService.findUserById(userId)
            if (user?.email) throw new ResourceNotFoundException("BARBER OR HAIRDRESSER NOT FOUND")

            if (user.permission == UsersPermission.ADMIN || user.permission == UsersPermission.ADMIN) {
                Reserves reserve = reserveRepository.findById(id).get()
                if (!reserve?.id) throw new ResourceNotFoundException("RESERVE NOT FOUND")

                WorkServices work = workService.findByIdToEditWorkServiceOrCancel(reserve.workServiceId)
                if (!work?.user_id) throw new ResourceNotFoundException("WORK SERVICE RESERVED NOT FOUND")
                workService.cancel(work)

                reserve.isActive = false
                reserve.reserveStatus = WorkServiceStatus.CANCELED
                reserve.cancelOn = Instant.now()

                reserveRepository.save(reserve)
                registerLoginService.cancelReserve(user)
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO create(ReserveRequestDTO body) {

        // check client id if exist
        if (!clientService.findById(body.clientId)) throw new ResourceNotFoundException("CLIENT_NOT_FOUND")

        //todo: check if dont have another reserve for the same day,
        //todo: grabar accion del cliente - CREATE NEW RERSERVE.

        Reserves reserve = createReserve(body)

        // Send Email
        SendReserveNotificationEmail(reserve)
        def savedReserve = reserveRepository.save(reserve)
        return decoratorPatternReserve(savedReserve)
    }

    //TODO: logic
    ReserveResponseDTO update(ReserveRequestDTO body, Long clientId) {
        def existentClient = clientRepository.findById(clientId)
        if (!existentClient.isPresent()) throw new ResourceNotFoundException("CLIENT NOT FOUND")

        Client client = updateClient(existentClient.get(), body)
        updateUser(body)

        def savedClient = clientRepository.save(client)
        return decoratorPatternClient(savedClient)
    }

    //TODO: Algunos metodos para hacer.
    void updateAmountReserve() {}

    void updateMetrics() {}

    void generateMetrics() {}

    protected Reserves createReserve(ReserveRequestDTO request) {
        Double totalCost = calculateTotalCost(request.priceService, request.productCost, request.externalServicesCost, 0)
        new Reserves(
                userId: request.userId,
                enterpriseId: request?.enterpriseId ?: 1,
                clientId: request.clientId,
                clientName: request.clientName,
                clientPhone: request.clientPhone,
                emailClient: request.emailClient,
                socialNumber: request.socialNumber,
                duration: Time.valueOf(request.duration), //hh:mm
                reserveDatetime: request.reserveDatetime,
                barberService: request?.barberService,
                hairdresserService: request?.hairdresserService,
                promos: request?.promos,
                priceService: request?.priceService,
                underPromotion: request.underPromotion,
                productCost: request.productCost,
                externalServicesCost: request.externalServicesCost,
                workServiceId: workService.create(createWorkServiceReq(request, totalCost)).id,
                createdOn: Instant.now()
        )
    }

    protected static ServicesRequestDTO createWorkServiceReq(reqReserve, totalCost) {
        new ServicesRequestDTO(
                service_name: reqReserve.barberService ?: reqReserve.hairdresserService,
                barber_service: reqReserve?.barberService,
                hairdresser_service: reqReserve?.hairdresserService,
                promos: reqReserve?.promos,
                price_service: reqReserve?.priceService,
                total_cost: totalCost,
                under_promotion: reqReserve?.underPromotion,
                duration_of_service: Time.valueOf(reqReserve.duration),
                start: reqReserve?.reserveDatetime,
                user_id: reqReserve.userId,
                username: reqReserve.employeeUsername,
                client_id: reqReserve.clientId,
                client_name: reqReserve.clientName
        )
    }

    protected ReserveResponseDTO decoratorPatternReserve(Reserves res) {
        def service = workService.findById(res?.workServiceId)
        new ReserveResponseDTO(
                id: res?.id,
                enterprise: enterpriseService.findById(res.enterpriseId),
                barberUser: userService.findUserToFillReserve(res.userId),
                client: clientService.findById(res?.clientId),
                workService: service,
                priceService: res?.priceService ?: calculatePrice(res.barberService, res.hairdresserService),
                productCost: res?.productCost ?: 0,
                externalServicesCost: res?.externalServicesCost ?: 0,                                 // Cafeteria, bebidas, etc,
                totalCost: calculateTotalCost(res.priceService, res.productCost, res.externalServicesCost, service?.totalCost),                                            //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
                isActive: res.isActive
        )
    }

    protected static Double calculatePrice(barberPrice, hairdresserPrice) {
        return barberPrice.value ?: hairdresserPrice.value
    }

    protected static calculateTotalCost(priceService, productCost, externalServicesCost, serviceTotalCost) {
        return priceService + productCost + externalServicesCost + serviceTotalCost
    }

    protected static LocalDate convertToLocalDateTime(String date) {
        String alteredDate = date.replace("-", "/")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate newFormatDate = LocalDate.parse(alteredDate, formatter)
        return newFormatDate
    }

    //todo: mover estos al mailing service
    // Notification methods

    //todo modify this logic.-
    protected void SendReserveNotificationEmail(Reserves reserveDetails) {
        final StringBuilder reserveFormat = new StringBuilder()
        final StringBuilder dateReserve = new StringBuilder()
        Barber barber = getBarber(reserveDetails.getBarberOrHairdresserId())

        final String nameBarber = barber.getName()
        final String instagramBarber = barber.getInstagram()
        final String facebookBarber = barber.getFacebook()
        final String date = LocalDate.from(reserveDetails.getStartTime()).toString()
        final String time = LocalTime.from(reserveDetails.getStartTime()).toString()
        reserveFormat
                .append("<li type=\"square\"> Reserva creada por: ").append(reserveDetails.getNameClient() + "</li>")
                .append("<li type=\"square\"> Tu Reserva con ").append(nameBarber).append(" fue agendada con exito!").append("</li>")
                .append("<li type=\"square\"> L@ esperamos en Dr César Piovene 1027, Pando, Departamento de Canelones, Uruguay.").append("</li>")
                .append("<li type=\"square\"> Puedes seguir a ").append(nameBarber).append(" en sus redes sociales y estar actualizado con las ultimas tendencias!")
                .append("<li type=\"square\"> Instagram: ").append(Objects.nonNull(instagramBarber) ? instagramBarber : "https://www.instagram.com/artexperiencee/").append("</li>")
                .append("<li type=\"square\"> Facebook: ").append(Objects.nonNull(facebookBarber) ? facebookBarber : "https://www.facebook.com/artexperiencee/").append("</li>")

        dateReserve
                .append("<li type=\"square\"> Fecha: ").append(date).append("</li>")
                .append("<li type=\"square\"> Hora: ").append(time).append("</li>")

        sendMailService.notifyAndSendEmail(reserveFormat.toString(), reserveDetails.getNameClient(), dateReserve.toString(), reserveDetails.getMailClient())
    }

    //todo: mover estos al mailing service
    void testMail() {
        sendMailService.notifyAndSendEmail("<li type=\"square\">Esto es un Test del email</li>", "Juan Miguel", "<li type=\"square\">Fecha: 21-08-2020</li> <li type=\"square\">Hora: 12:00 hrs</li>", "")
    }
}
