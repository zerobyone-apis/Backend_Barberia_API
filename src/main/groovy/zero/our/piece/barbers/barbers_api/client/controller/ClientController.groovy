package zero.our.piece.barbers.barbers_api.client.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api._security.service.ConfirmationTokenService
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientRequestDTO
import zero.our.piece.barbers.barbers_api.client.service.ClientService

@Slf4j
@RestCrossOriginController("/client")
class ClientController {

    @Autowired
    ClientService clientService

    @Autowired
    ConfirmationTokenService confirmationTokenService

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('cli_resv_read')")
    def getClients() {
        clientService.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('cli_resv_read')")
    def getClientById(@PathVariable("id") Long clientId) {
        clientService.findById(clientId);
    }

    /**
     * @public Solo para que registren clientes
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    def create(@RequestBody ClientRequestDTO body) {
        clientService.create(body);
    }

    /**
     * @public Solo para que los clientes confirmen su registro
     */
    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    def confirm(@PathVariable("token") String token) {
        confirmationTokenService.verifyToken(token);
    }


    @PutMapping("/update/by/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('cli_resv_write', 'brb_write')")
    def update(@RequestBody ClientRequestDTO client, @PathVariable("id") Long id) {
        clientService.update(client, id);
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('cli_resv_read', 'brb_write')")
    def findByEmail(@PathVariable("email") String email) {
        clientService.findByEmail(email);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('cli_resv_read', 'brb_write')")
    def getClientByUsername(@PathVariable("username") String username) {
        clientService.findByUsername(username);
    }

    @PatchMapping("/remove/by/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    void logicDeleteById(@PathVariable("id") Long id) {
        clientService.logicDelete(id);
    }


    @DeleteMapping("/deactivate/by/id/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    void deactivateAccount(@PathVariable("clientId") Long id) {
        clientService.logicDelete(id);
    }


}
