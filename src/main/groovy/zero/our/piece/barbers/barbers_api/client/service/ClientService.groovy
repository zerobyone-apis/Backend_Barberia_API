package zero.our.piece.barbers.barbers_api.client.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.repository.ClientRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException

@Service
@Slf4j
class ClientService {

    @Autowired
    ClientRepository clientRepository

    List<ClientResponseDTO> findAll() {
        clientRepository.findAll().each { it ->
            decoratorPatternClient(it)
        } as List<ClientResponseDTO>
    }

    ClientResponseDTO findById(Long id) {
        def foundUser = clientRepository.findById(id).get()
        if (!foundUser?.id) throw new ResourceNotFoundException("USER_NOT_FOUND")

        return decoratorPatternClient(foundUser)
    }

    ClientResponseDTO findByUserId(Long userId) {
        Client client = clientRepository.findByUserId(userId)
        if (!client?.id) throw new ResourceNotFoundException("Barber with this User ID Not found: " + userId)

        return decoratorPatternClient(client)
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
