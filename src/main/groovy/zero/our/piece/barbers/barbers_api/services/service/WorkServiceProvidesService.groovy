package zero.our.piece.barbers.barbers_api.services.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._configuration.RestCrossOriginController
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesRequestDTO
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesResponseDTO
import zero.our.piece.barbers.barbers_api.services.model.WorkServices
import zero.our.piece.barbers.barbers_api.services.repository.ServicesRepository

import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

@Service
@Slf4j
class WorkServiceProvidesService {

    @Autowired
    ServicesRepository servicesRepository

    List<ServicesResponseDTO> findAll() {
        try {
            servicesRepository.findAll().collect { it -> decoratorPatternServices(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    List<ServicesResponseDTO> findAllByStatus(WorkServiceStatus status) {
        try {
            servicesRepository.findAll().stream().filter { it.status == status }.collect { it -> decoratorPatternServices(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ServicesResponseDTO findById(Long id) {
        try {
            def service = servicesRepository.findById(id).get()
            return decoratorPatternServices(service)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    ServicesResponseDTO create(ServicesRequestDTO req) {
        try {
            def service = createWorkService(req)
            def saved = servicesRepository.save(service)
            return decoratorPatternServices(saved)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new CreateResourceException(ex.message)
        }
    }

    ServicesResponseDTO update(ServicesRequestDTO req, Long serviceId) {
        try {
            WorkServices existent = servicesRepository.findById(serviceId).get()
            if (!existent?.userId) throw new ResourceNotFoundException("WORK_SERVICE_NOT_FOUND")

            def service = updateWorkService(existent, req)
            def saved = servicesRepository.save(service)
            return decoratorPatternServices(saved)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new CreateResourceException(ex.message)
        }
    }

    WorkServices findByIdToEditWorkServiceOrCancel(Long id) {
        try {
            def service = servicesRepository.findById(id).get()
            return service
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    void cancel(WorkServices service) {
        try {
            service.status = WorkServiceStatus.CANCELED
            service.canceledOn = Instant.now()
            servicesRepository.save(service)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    protected static createWorkService(ServicesRequestDTO requestDTO) {
        return new WorkServices(
                description: requestDTO.description,
                barberService: requestDTO.barberService,
                hairdresserService: requestDTO.hairdresserService,
                promos: requestDTO.promos,
                priceService: requestDTO.priceService ?: calculatePrice( requestDTO.barberService , requestDTO.hairdresserService ),
                productCost: requestDTO.productCost ?: 0.0,
                externalServicesCost: requestDTO.externalServicesCost ?: 0.0,
                underPromotion: requestDTO.underPromotion ?: false,
                totalCost: requestDTO.underPromotion ? calculateTotal(requestDTO.priceService, requestDTO.productCost, requestDTO.externalServicesCost, requestDTO.promos) : requestDTO.priceService,
                duration: requestDTO.duration,
                start: requestDTO.start,
                finish: calcularHoraDeFinalizacion(requestDTO.start,requestDTO.duration ),
                userId: requestDTO.userId,
                username: requestDTO.username,
                clientId: requestDTO.clientId,
                clientName: requestDTO.clientName,
                clientPhone: requestDTO.clientPhone,
                socialNumber: requestDTO.socialNumber,
                createdOn: Instant.now(),
                status: WorkServiceStatus.IN_PROGRESS
        )
    }

    protected static updateWorkService(existent,  requestDTO) {
        existent.description = requestDTO.description ?: ( requestDTO.barberService ?: requestDTO.hairdresserService )
        existent.barberService = requestDTO?.barberService
        existent.hairdresserService = requestDTO?.hairdresserService
        existent.promos = requestDTO?.promos
        existent.priceService = requestDTO.priceService ?: calculatePrice( requestDTO.barberService , requestDTO.hairdresserService )
        existent.productCost = requestDTO.productCost ?: 0.0
        existent.externalServicesCost = requestDTO.externalServicesCost ?: 0.0
        existent.underPromotion = requestDTO.underPromotion ?: false
        existent.totalCost = requestDTO.underPromotion ? calculateTotal(requestDTO.priceService, requestDTO.productCost, requestDTO.externalServicesCost, requestDTO.promos) : requestDTO.priceService
        existent.duration = requestDTO.duration
        existent.start = requestDTO.start
        existent.finish = LocalDateTime.from(requestDTO.start).plusMinutes(requestDTO.duration.time)
        existent.userId = requestDTO.userId
        existent.username = requestDTO.username
        existent.clientName = requestDTO.clientName
        existent.clientPhone = requestDTO.clientPhone
        existent.clientEmail = requestDTO.clientEmail
        existent.socialNumber = requestDTO.socialNumber
        existent.updatedOn = Instant.now()
        existent.status = WorkServiceStatus.IN_PROGRESS

        existent
    }

    protected static Double calculateTotal(servicePrice,productCost,externalCost, promos) {
        /*
            TODO:
                Crear el servicio de Promociones o modificar el enum con valores correctos.
                promos deberian de ser promociones de servicios con descuentos por lo que se deberia de llamar 'Discounts'
                    Ej:     name = CORTE_BARBA , value = ' 10 % '
                            name = CORTE_CEJAS , value = ' 20 % '
         */
        // fixme: Algo para revisar si la promo es por un producto o aplica a todo el costo.
        def sumPrice = servicePrice + productCost + externalCost
        def discount = sumPrice * promos.value / 100.0
        return sumPrice - discount


    }

    protected static Double calculatePrice(barberPrice, hairdresserPrice) {
        return barberPrice.value ?: hairdresserPrice.value
    }

    protected static ServicesResponseDTO decoratorPatternServices(WorkServices res) {
        new ServicesResponseDTO(
                id: res?.id,
                description: res?.description,
                barberService: res?.barberService,
                hairdresserService: res?.hairdresserService,
                promos: res?.promos,
                priceService: res?.priceService,
                productCost: res?.productCost,
                externalServicesCost: res?.externalServicesCost,
                totalCost: res?.totalCost,
                underPromotion: res?.underPromotion,
                durationOfService: res?.duration,
                start: res?.start,
                finish: res?.finish,
                userId: res?.userId,
                employeeUsername: res?.username,
                clientId: res?.clientId,
                clientName: res?.clientName,
                clientPhone: res?.clientPhone,
                socialNumber: res?.socialNumber,
                clientEmail: res?.clientEmail,
                status: res?.status
        )
    }

    protected static calcularHoraDeFinalizacion(start , duration){
        Long minutes = Time.valueOf(duration).time
        LocalDateTime.from(start).plusMinutes(minutes)
    }

}
