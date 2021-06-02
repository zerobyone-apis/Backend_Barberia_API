package zero.our.piece.barbers.barbers_api.client.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientRequestDTO
import zero.our.piece.barbers.barbers_api.client.service.ClientService

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


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    def update(@RequestBody ClientRequestDTO client, @PathVariable("id") Long id) {
        clientService.update(client, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void logicDeleteById(@PathVariable("id") Long id) {
           clientService.logicDelete(id);
    }

/*
    //todo: get by email
    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    def findByEmail(@PathVariable("email") String email) {
        clientService.findByEmail(email);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    def getClientByUsername(@PathVariable("username") String username) {
        clientService.findByUsername(username);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable("id") Long id) {
        clientService.logicalDeleted(id);
    }
*/

}
