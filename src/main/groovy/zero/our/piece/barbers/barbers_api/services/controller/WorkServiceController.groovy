package zero.our.piece.barbers.barbers_api.services.controller

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesRequestDTO
import zero.our.piece.barbers.barbers_api.services.service.WorkServiceProvidesService

import java.security.Principal

@Controller
@Slf4j
@RestCrossOriginController("/work_services")
class WorkServiceController {
    
    @Autowired
    WorkServiceProvidesService workService

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    def getAll(/*Principal principal*/) {
        workService.findAll();
    }

    @GetMapping('/all/status/{status}')
    @ResponseStatus(HttpStatus.OK)
    def getAllByStatus(/*Principal principal,*/ @PathVariable("status") WorkServiceStatus status) {
        workService.findAllByStatus(status);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    def getById(/*Principal principal,*/ @PathVariable("id") Long workServiceId) {
        workService.findById(workServiceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    def create(/*Principal principal,*/ @RequestBody ServicesRequestDTO request) {
        workService.create(request);
    }

    @PutMapping('/{serviceId}')
    @ResponseStatus(HttpStatus.CREATED)
    def update(/*Principal principal,*/ @RequestBody ServicesRequestDTO request, @PathVariable("serviceId") Long serviceId) {
        workService.update(request, serviceId);
    }

}
