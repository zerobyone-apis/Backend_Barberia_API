package zero.our.piece.barbers.barbers_api.client.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientRequestDTO
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.client.repository.ClientRepository
import zero.our.piece.barbers.barbers_api.client.service.ClientService
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.model.DTO.RequestUserLoginDTO
import zero.our.piece.barbers.barbers_api.user.model.User

@Slf4j
@RestCrossOriginController("/client")
class ClientController {

    @Autowired
    ClientService clientService


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    def getClients() {
        clientService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    def getClientById(@PathVariable("id") Long clientId) {
        clientService.findById(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    def create(@RequestBody ClientRequestDTO body) {
        clientService.create(body);
    }

    //todo: update
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    def update(@RequestBody Client client) {
      //  clientService.update(client);
    }

    //todo: get by email
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    def getClientByEmail(@PathVariable("email") String email) {
       // clientService.findByEmailId(email);
    }

    //todo: get by username
    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    def getClientByUsername(@PathVariable("username") String username) {
       // clientService.findByEmailId(username);
    }

    //todo: delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") Long id) {
     //   clientService.delete(id);
    }


}
