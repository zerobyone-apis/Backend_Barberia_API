package zero.our.piece.barbers.barbers_api.reserve.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.reserve.model.DTO.ReserveRequestDTO
import zero.our.piece.barbers.barbers_api.reserve.service.ReserveServices

import javax.sound.midi.Patch
import java.security.Principal

@Controller
@Slf4j
@RestCrossOriginController("/reserves")
class ReserveController {
    
    @Autowired
    ReserveServices reserveService

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    def getAll(/*Principal principal*/) {
        reserveService.findAll();
    }

    @GetMapping('/actives')
    @ResponseStatus(HttpStatus.OK)
    def getActives(/*Principal principal*/) {
        reserveService.findAllActives();
    }

    @GetMapping('/user/{id}/all')
    @ResponseStatus(HttpStatus.OK)
    def getAllByUserID(/*Principal principal,*/ @PathVariable("id") Long userId) {
        reserveService.findAllByUserId(userId);
    }

    @GetMapping('/user/{id}/all/actives')
    @ResponseStatus(HttpStatus.OK)
    def getAllActivesByUserID(/*Principal principal,*/ @PathVariable("id") Long userId) {
        reserveService.findAllActivesByUserId(userId);
    }

    @GetMapping('/client/{id}/all')
    @ResponseStatus(HttpStatus.OK)
    def getAllByClientID(/*Principal principal,*/ @PathVariable("id") Long clientId) {
        reserveService.findAllByClientId(clientId);
    }

    @GetMapping('/user/{id}/date/{date}')
    @ResponseStatus(HttpStatus.OK)
    def getSpecificByUserIdAndDate(/*Principal principal,*/ @PathVariable("id") Long userId, @PathVariable("date") String date) {
        reserveService.findByUserIdAndDate(userId, date);
    }

    @GetMapping("/client/{id}")
    @ResponseStatus(HttpStatus.OK)
    def getByClientId(/*Principal principal,*/ @PathVariable("id") Long clientId) {
        reserveService.findByClientId(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    def create(/*Principal principal,*/ @RequestBody ReserveRequestDTO request) {
        reserveService.create(request);
    }

    @PutMapping('/{id}')
    @ResponseStatus(HttpStatus.CREATED)
    def update(/*Principal principal,*/ @RequestBody ReserveRequestDTO request, @PathVariable("id") Long reserveId) {
        reserveService.update(request, reserveId);
    }

    @DeleteMapping("/{reserveId}/cancel/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    void cancelByUser(/*Principal principal,*/ @PathVariable("reserveId") Long reserveId, @PathVariable("userId") Long userId) {
        reserveService.cancel(reserveId, userId);
    }
    @DeleteMapping("/{id}/cancel/client/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    void cancelByClient(/*Principal principal,*/ @PathVariable("reserveId") Long reserveId, @PathVariable("clientId") Long clientId) {
        reserveService.cancelByClient(reserveId, clientId);
    }

    @PatchMapping("/mail/{email}/test")
    @ResponseStatus(HttpStatus.OK)
    void sendEmailTest(/*Principal principal,*/ @PathVariable("email") String email) {
        reserveService.testMail(email);
    }
}
