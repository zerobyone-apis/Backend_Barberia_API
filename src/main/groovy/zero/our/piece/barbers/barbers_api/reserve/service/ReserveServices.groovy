package zero.our.piece.barbers.barbers_api.reserve.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.audit.infrastructure.AuditAction
import zero.our.piece.barbers.barbers_api.audit.service.AuditService
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.barber.service.BarberService
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.enterprise.service.EnterpriseService
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.magicCube.mailing.SendMailService
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveRequestDTO
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveResponseDTO
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves
import zero.our.piece.barbers.barbers_api.reserve.repository.ReserveRepository
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesRequestDTO
import zero.our.piece.barbers.barbers_api.services.model.WorkServices
import zero.our.piece.barbers.barbers_api.services.service.WorkServiceProvidesService
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.Action
import zero.our.piece.barbers.barbers_api.user.service.RegisterLoginService
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.sql.Time
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Slf4j
class ReserveServices {

    @Autowired
    ReserveRepository reserveRepository

    @Autowired
    UserService userService

    @Autowired
    AuditService auditService

    @Autowired
    BarberService barberService

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

            List<Reserves> reserve = reserveRepository.findAllByClientIdAndIsActive(clientId, Boolean.TRUE)
            if (!reserve?.id) throw new ResourceNotFoundException("Reserve with this Client ID Not found: ${clientId}")

            return reserve.collect { it -> decoratorPatternReserve(it) }
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

            Reserves reserve = reserveRepository.findByUserIdAndCreatedOn(userId, date)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ${userId} for this Date ${date} Not found")

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findByClientId(Long clientId) {
        try {

            Reserves reserve = reserveRepository.findByClientIdAndCreatedOn(clientId, Instant.now())
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: " + clientId)

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO create(ReserveRequestDTO body) {

        // check client id if exist
        ClientResponseDTO client = clientService.findById(body.clientId)
        if (!client?.name) throw new ResourceNotFoundException("CLIENT_NOT_FOUND")

        /**
         TODO:
         Revisar si tiene reservas para el mismo dia
         */

        Reserves reserve = createReserve(body)
        auditService.saveClientLog(client, AuditAction.CLIENT_CREATE_RESERVE)
        def savedReserve = reserveRepository.save(reserve)

        // Send Email
        SendReserveNotificationEmail(reserve)
        return decoratorPatternReserve(savedReserve)
    }

    ReserveResponseDTO update(ReserveRequestDTO body, Long reserveId) {

        ClientResponseDTO client = clientService.findById(body.clientId)
        Reserves existent = reserveRepository.findById(reserveId).get()
        if (!existent.isActive) throw new ResourceNotFoundException("CLIENT_NOT_FOUND")

        Reserves updated = updateReserve(existent, body)
        auditService.saveClientLog(client, AuditAction.CLIENT_UPDATE_RESERVE)
        def savedReserve = reserveRepository.save(updated)

        // Send Email
        SendReserveNotificationEmail(updated)
        return decoratorPatternReserve(savedReserve)
    }

    void cancel(Long id, Long userId) {
        try {
            User user = userService.findUserById(userId)
            if (user?.email) throw new ResourceNotFoundException("BARBER OR HAIRDRESSER NOT FOUND")

            if (user.roles == UsersRoles.ADMIN || user.roles == UsersRoles.ADMIN) {
                Reserves reserve = reserveRepository.findById(id).get()
                if (!reserve?.id) throw new ResourceNotFoundException("RESERVE NOT FOUND")

                WorkServices work = workService.findByIdToEditWorkServiceOrCancel(reserve.workServiceId)
                if (!work?.userId) throw new ResourceNotFoundException("WORK SERVICE RESERVED NOT FOUND")
                workService.cancel(work)

                reserve.isActive = false
                reserve.requestedCancel = true
                reserve.cancelOn = Instant.now()

                reserveRepository.save(reserve)
                registerLoginService.cancelReserve(user, Action.USER_CANCEL_RESERVES)
            }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    void cancelByClient(Long id, Long clientId) {
        try {
            ClientResponseDTO client = clientService.findById(clientId)

            Reserves reserve = reserveRepository.findById(id).get()
            if (!reserve?.id) throw new ResourceNotFoundException("RESERVE NOT FOUND")

            WorkServices work = workService.findByIdToEditWorkServiceOrCancel(reserve.workServiceId)
            if (!work?.userId) throw new ResourceNotFoundException("WORK SERVICE RESERVED NOT FOUND")
            workService.cancel(work)

            reserve.isActive = false
            reserve.requestedCancel = true
            reserve.cancelOn = Instant.now()

            reserveRepository.save(reserve)
            auditService.saveClientLog(client, AuditAction.CLIENT_CANCEL_RESERVES)

        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    /**-----------------------------------------------------------------------------------------------------------------*/

    /** TODO: METRICS */
    void updateAmountReserve() {}

    void updateMetrics() {}

    void generateMetrics() {}

    protected Reserves createReserve(ReserveRequestDTO request) {
        new Reserves(
                userId:             request.userId,
                employeeUsername:   request.employeeUsername,
                enterpriseId:       request?.enterpriseId ?: 1,
                clientId:           request.clientId,
                duration:           request.duration, //hh:mm
                reserveDatetime:    convertToLocalDateTime(request.reserveDatetime),
                workServiceId:      workService.create(createWorkServiceReq(request)).id,
                createdOn:          Instant.now(),
                isActive:           Boolean.TRUE
        )
    }

    protected Reserves updateReserve(existent, request) {

        existent.userId                 = request?.userId
        existent.employeeUsername       = request?.employeeUsername
        existent.enterpriseId           = request?.enterpriseId ?: 1
        existent.clientId               = request?.clientId
        existent.duration               = request.duration //hh:mm
        existent.reserveDatetime        = request?.reserveDatetime
        existent.workServiceId          = workService.update(createWorkServiceReq(request), existent.id).id
        existent.updateOn               = Instant.now()

        existent
    }

    protected static ServicesRequestDTO createWorkServiceReq(ReserveRequestDTO reqReserve) {
        Double totalCost = calculateTotalCost(reqReserve.priceService, reqReserve.productCost, reqReserve.externalServicesCost)
        new ServicesRequestDTO(
                description: reqReserve.descripcion ?: (reqReserve.barberService ?: reqReserve.hairdresserService),
                barberService: reqReserve?.barberService,
                hairdresserService: reqReserve?.hairdresserService,
                promos: reqReserve?.promos,
                priceService: reqReserve?.priceService,
                productCost: reqReserve?.productCost,
                externalServicesCost: reqReserve?.externalServicesCost,
                totalCost: totalCost,
                underPromotion: reqReserve?.underPromotion,
                duration: reqReserve.duration,
                start: convertToLocalDateTime(reqReserve.reserveDatetime),
                userId: reqReserve.userId,
                username: reqReserve.employeeUsername,
                clientId: reqReserve.clientId,
                clientName: reqReserve.clientName,
                clientPhone: reqReserve.clientPhone,
                clientEmail: reqReserve.emailClient,
                socialNumber: reqReserve.socialNumber
        )
    }

    protected ReserveResponseDTO decoratorPatternReserve(Reserves res) {
        new ReserveResponseDTO(
                id: res?.id,
                enterprise: enterpriseService.findById(res.enterpriseId),
                barberUser: userService.findUserToFillReserve(res.userId),
                client: clientService.findById(res?.clientId),
                workService: workService.findById(res?.workServiceId),
                isActive: res.isActive
        )
    }

    protected static Double calculateTotalCost(priceService, productCost, externalServicesCost) {
        return priceService + productCost + externalServicesCost
    }

    //TODO: Planificar un servicio de notificaciones en los cuales dependiendo el tipo de notificacion el mail que se enviara para ello debemos tener un sistema de tamplates por Notificacion.
    protected void SendReserveNotificationEmail(Reserves reserveDetails) {

        BarberResponseDTO barber = getBarber(reserveDetails.userId)

        String instagramBarberUrl = "https://www.instagram.com/${barber.inst_username}"
        String facebookBarberUrl = "https://www.facebook.com/${barber.facebook_username}"

        def reserveDate = """
                              <li type=\"square\"> Fecha: ${reserveDetails.reserveDatetime.toLocalDate()}</li>
                              <li type=\"square\"> Hora: ${reserveDetails.reserveDatetime.toLocalTime()}</li>
                          """
        def formatReserve = """
                                <li type=\"square\"> Reserva creada por: ${reserveDetails.clientName}</li>
                                <li type=\"square\"> Tu Reserva con ${barber.name} fue agendada con exito!</li>
                                <li type=\"square\"> L@ esperamos en Dr César Piovene 1027, Pando, Departamento de Canelones, Uruguay.</li>
                                <li type=\"square\"> Puedes seguir a ${barber.name} en sus redes sociales y estar actualizado con las ultimas tendencias!
                                <li type=\"square\"> Instagram: ${instagramBarberUrl ?: "https://www.instagram.com/artexperiencee/"} </li>
                                <li type=\"square\"> Facebook: ${facebookBarberUrl ?: "https://www.facebook.com/artexperiencee/"} </li>
                            """
        sendMailService.notifyAndSendEmail(formatReserve, reserveDetails.clientName, reserveDate, reserveDetails.emailClient)
    }

    void testMail(String recipientEmail) {
        def detailsMessage = "<li type=\"square\">Esto es un Test del email</li>"
        def username = "Zero Dev"
        def dateTime = "<li type=\"square\">Fecha: 21-08-2020</li> <li type=\"square\">Hora: 12:00 hrs</li>"
        def defaultEmail = "zerobyone.dev@gmail.com"
        sendMailService.notifyAndSendEmail(detailsMessage, username, dateTime, recipientEmail ?: defaultEmail)
    } // todo: Endpoint para mandar mails a discrecion

    protected BarberResponseDTO getBarber(userId) {
        barberService.findByUserId(userId)
    }

    protected static LocalDateTime convertToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        LocalDateTime newFormatDate = LocalDateTime.parse(date, formatter)
        return newFormatDate
    }
}
