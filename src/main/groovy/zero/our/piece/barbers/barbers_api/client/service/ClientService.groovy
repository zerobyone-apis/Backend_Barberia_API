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
            clientRepository.findAll()
                    .stream()
                    .map({ it -> decoratorPatternClient(it) })
                    .collect(Collectors.toList())
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
                client_type: ClientType.NEW_CLIENT
        )
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
