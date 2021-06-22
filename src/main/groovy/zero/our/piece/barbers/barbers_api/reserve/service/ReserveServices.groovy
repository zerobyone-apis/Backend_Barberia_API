package zero.our.piece.barbers.barbers_api.reserve.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientRequestDTO
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.repository.ClientRepository
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.enterprise.service.EnterpriseService
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveRequestDTO
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveResponseDTO
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves
import zero.our.piece.barbers.barbers_api.reserve.repository.ReserveRepository
import zero.our.piece.barbers.barbers_api.services.service.WorkServiceProvidesService
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.time.Instant

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

    List<ReserveResponseDTO> findAll() {
        try {
            reserveRepository.findAll().collect {it -> decoratorPatternReserve(it)}
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllActives() {
        try {
            reserveRepository.findAll().stream().filter{it.isActive }.collect {it -> decoratorPatternReserve(it)}
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

    /*
        This endpoint will be for specific endpoint to know about latest reserve
     */
    ReserveResponseDTO findByUserId(Long userId , String date) {
        try {
            //todo: create the query to db
            Reserves reserve = reserveRepository.findByUserId(userId, date)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ${userId} for this Date ${date} Not found")

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findByClientId(Long userId) {
        try {
            //todo: create the query to db
            Reserves reserve = reserveRepository.findByUserId(userId)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: " + userId)

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllReservesByUserId(Long userId) {
        try {
            //todo: create the query to db
            Reserves reserve = reserveRepository.findAllByUserId(userId)
            if (!reserve?.id) throw new ResourceNotFoundException("reserve with this User ID Not found: ${userId}")

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ReserveResponseDTO> findAllReservesByClientId(Long clientId) {
        try {
            //todo: create the query to db
            Reserves reserve = reserveRepository.findAllByClientId(clientId)
            if (!reserve?.id) throw new ResourceNotFoundException("Reserve with this Client ID Not found: ${clientId}")

            return decoratorPatternReserve(reserve)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ReserveResponseDTO findByEmail(String email) {
        try {
            //todo: create the query to db
            Client client = clientRepository.findByEmail(email)
            if (!client?.id) throw new ResourceNotFoundException("Client with this Email Not found: " + email)

            return decoratorPatternClient(client)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }





    //todo:
    ReserveResponseDTO findByUsername(String username) {
        try {
            Client client = clientRepository.findByUsername(username)
            if (!client?.id) throw new ResourceNotFoundException("Client with this Username Not found: " + username)

            return decoratorPatternClient(client)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    //todo: terminar este metodo a medias.
    void logicalDeleted(Long id) {
        try {
            Optional<Reserves> reserve = reserveRepository.findById(id)
            if (!reserve.isPresent()) throw new ResourceNotFoundException("RESERVE NOT FOUND")
            reserve.get().is_active = false
            reserve.get().updated_on = Instant.now()

            userService.delete(client.get().user_id)
            clientRepository.save(client.get())
            log.info("Logical delete successfully")
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    //TODO:
    ReserveResponseDTO create(ReserveRequestDTO body) {
        Client client = createClient(body)
        User user = createUser(body)
        client.user_id = user.id
        client.social_number = user.social_number

        def savedReserve = clientRepository.save(client)
        user.client_id = savedReserve.id
        userService.saveUser(user, 'Updating clientID')

        return decoratorPatternReserve(savedReserve)
    }

    //TODO:
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

    void logicDelete(Long clientId) {
        try {
            Client client = clientRepository.findById(clientId).get()
            client.is_active = false
            userService.delete(client.user_id)
            clientRepository.save(client)
        } catch (Exception ex) {
            log.error("ERROR: Trying to save User due to: ${ex.getMessage()}")
            throw new CreateResourceException("Error: ${ex.getMessage()}")
        }

    }


/*    protected static Client createClient(client) {
        new Client(
                name: client.name,
                username: client.username,
                phone: client.phone,
                email: client.email,
                inst_username: client?.inst_username,
                inst_image_profile_url: client?.inst_image_profile_url,
                inst_photo_url: client?.inst_photo_url,
                accept_integration: client.accept_integration ?: false,
                client_type: ClientType.NEW_CLIENT,
                created_on: Instant.now()
        )
    }

    protected static Client updateClient(Client existent, newClient) {

        existent.name = newClient.name
        existent.username = newClient.username
        existent.phone = newClient.phone
        existent.email = newClient.email
        existent.inst_username = newClient?.inst_username
        existent.inst_image_profile_url = newClient?.inst_image_profile_url
        existent.inst_photo_url = newClient?.inst_photo_url
        existent.accept_integration = newClient.accept_integration ?: false
        //existent.client_type =  ClientType.NEW_CLIENT //todo count reserve to evaluete type of client

        existent
    }

    protected User createUser(client) {
        def user = userService.createUser(
                new User(
                        username: client.username,
                        password: client.password,
                        email: client.email,
                        enterprise_id: client.enterprise_id
                ))
        return userService.saveUser(user, 'CREATE')
    }

    protected User updateUser(client) {
        User existentUser
        try {
            existentUser = userService.findByEmail(client.username)
            def user = userService.updateUser(
                    new User(
                            id: existentUser.id,
                            username: client.username,
                            password: client.password,
                            email: client.email,
                            enterprise_id: client.enterprise_id
                    ))

            return userService.saveUser(user, 'UPDATE')
        } catch (Exception ex) {
            throw new CreateResourceException("User with Username: ${client.username} Not Exists... ERROR: $ex.message",)
        }
    }*/

    protected ReserveResponseDTO decoratorPatternReserve(Reserves res) {
        def service = workService.findById(res?.workServiceId)
        new ReserveResponseDTO(
                id: res?.id,
                enterprise: enterpriseService.findById(res.enterpriseId),
                barberUser: userService.findUserToFillReserve(res.userId),
                client: clientService.findById(res?.clientId),
                workService:  service,
                priceService: res?.priceService ?: calculatePrice(res.barberService, res.hairdresserService) ,
                productCost: res?.productCost ?: 0,
                externalServicesCost: res?.externalServicesCost ?: 0,                                 // Cafeteria, bebidas, etc,
                totalCost: calculateTotalCost(res.priceService, res.productCost, res.externalServicesCost, service.totalCost),                                            //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
                isActive: res.isActive

        )
    }
    protected static Double calculatePrice(barberPrice, hairdresserPrice) {
        return barberPrice.value ?: hairdresserPrice.value
    }

    protected static calculateTotalCost(priceService, productCost, externalServicesCost, serviceTotalCost ){
        return priceService + productCost + externalServicesCost + serviceTotalCost
    }

}
