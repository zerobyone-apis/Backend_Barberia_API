package zero.our.piece.barbers.barbers_api.client.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientRequestDTO
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.repository.ClientRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.time.Instant
import java.util.stream.Collectors

@Service
@Slf4j
class ClientService {

    @Autowired
    ClientRepository clientRepository

    @Autowired
    UserService userService

    List<ClientResponseDTO> findAll() {
        try {
            clientRepository.findAll().collect {it -> decoratorPatternClient(it)}
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ClientResponseDTO findById(Long id) {
        try {
            def foundUser = clientRepository.findById(id).get()
            if (!foundUser?.id) throw new ResourceNotFoundException("USER_NOT_FOUND")

            return decoratorPatternClient(foundUser)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ClientResponseDTO findByUserId(Long userId) {
        try {
            Client client = clientRepository.findByUserId(userId)
            if (!client?.id) throw new ResourceNotFoundException("Client with this User ID Not found: " + userId)

            return decoratorPatternClient(client)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ClientResponseDTO findByEmail(String email) {
        try {
            Client client = clientRepository.findByEmail(email)
            if (!client?.id) throw new ResourceNotFoundException("Client with this Email Not found: " + email)

            return decoratorPatternClient(client)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ClientResponseDTO findByUsername(String username) {
        try {
            Client client = clientRepository.findByUsername(username)
            if (!client?.id) throw new ResourceNotFoundException("Client with this Username Not found: " + username)

            return decoratorPatternClient(client)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    void logicalDeleted(Long id) {
        try {
            Optional<Client> client = clientRepository.findById(id)
            if (!client.isPresent()) throw new ResourceNotFoundException("CLIENT NOT FOUND")
            client.get().is_active = false
            client.get().updated_on = Instant.now()

            userService.delete(client.get().user_id)
            clientRepository.save(client.get())
            log.info("Logical delete successfully")
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ClientResponseDTO create(ClientRequestDTO body) {
        Client client = createClient(body)
        User user = createUser(body)
        client.user_id = user.id
        client.social_number = user.social_number

        def savedClient = clientRepository.save(client)
        user.client_id = savedClient.id
        userService.saveUser(user, 'Updating clientID')

        return decoratorPatternClient(savedClient)
    }

    ClientResponseDTO update(ClientRequestDTO body, Long clientId) {
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


    protected static Client createClient(client) {
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
    }

    protected static ClientResponseDTO decoratorPatternClient(Client client) {
        new ClientResponseDTO(
                id: client?.id,
                name: client?.name,
                username: client?.username,
                email: client?.email,
                phone: client?.phone,
                social_number: client?.social_number,
                client_type: client?.client_type,
                inst_username: client?.inst_username,
                inst_photo_url: client?.inst_photo_url,
                inst_image_profile_url: client?.inst_image_profile_url,
                accept_integration: client?.accept_integration,
        )
    }

}
