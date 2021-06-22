package zero.our.piece.barbers.barbers_api.barber.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberRequestDTO
import zero.our.piece.barbers.barbers_api.barber.service.BarberService

import java.security.Principal

@Slf4j
@RestCrossOriginController("/barber")
class BarberController {

    @Autowired
    BarberService service

    //TODO: Only admins can use this endpoint create ADMIN security rol
    @GetMapping()
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def getBarbers(Principal principal) {
        //def user = securityService.getLoggedUser(principal)
        service.findAll()
    }

    @GetMapping("/actives")
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def getActiveBarbers(Principal principal) {
        //def user = securityService.getLoggedUser(principal)
        service.findAllActives()
    }

    @GetMapping("/{id}")
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def getBarberById(Principal principal, @PathVariable("id") Long barberId) {
        //def user = securityService.getLoggedUser(principal)
        service.findById(barberId)
    }

    @GetMapping("/{user_id}")
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def getBarberUserById(Principal principal, @PathVariable("user_id") Long userId) {
        //def user = securityService.getLoggedUser(principal)
        service.findByUserId(userId)
    }

    @PostMapping
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def create(Principal principal, @RequestBody BarberRequestDTO barber) {
        //def user = securityService.getLoggedUser(principal)
        service.create(barber)
    }

    @PutMapping("/{barber_id}")
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    def update(Principal principal, @RequestBody BarberRequestDTO barber, @PathVariable("barber_id") Long barberId) {
        //def user = securityService.getLoggedUser(principal)
        service.update(barber, barberId)
    }


    //TODO: Only admins can use this endpoint create ADMIN security rol
    @PatchMapping("/deactivate/{barber_id}")
    //@PreAuthorize('hasAuthorities()')
    @ResponseStatus(HttpStatus.OK)
    void logicDeleteById(Principal principal, @PathVariable("barber_id") Long id) {
        //def user = securityService.getLoggedUser(principal)
        service.logicDelete(id);
    }




}
