package zero.our.piece.barbers.barbers_api.services.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.enterprise.service.EnterpriseService
import zero.our.piece.barbers.barbers_api.magicCube.exception.CreateResourceException
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesRequestDTO
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesResponseDTO
import zero.our.piece.barbers.barbers_api.services.model.WorkServices
import zero.our.piece.barbers.barbers_api.services.repository.ServicesRepository
import zero.our.piece.barbers.barbers_api.user.service.UserService

import java.time.Instant
import java.time.LocalDateTime

@Service
@Slf4j
class WorkServiceProvidesService {

    @Autowired
    ServicesRepository servicesRepository

    @Autowired
    UserService userService

    @Autowired
    EnterpriseService enterpriseService

    List<ServicesResponseDTO> findAll() {
        try {
            servicesRepository.findAll().collect { it -> decoratorPatternServices(it) }
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

    List<ServicesResponseDTO> findAllByStatus(WorkServiceStatus status) {
        try {
            servicesRepository.findAll().stream().filter { it.status == status }.collect { it -> decoratorPatternServices(it) }
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new ResourceNotFoundException(ex.message)
        }
    }

    //TODO: Revisar si es necesario hacer un trace de todo el proceso de creacion de reserva y guardarlo en una tabla de 'activities'.
    ServicesResponseDTO create(ServicesRequestDTO req) {
        try {
            def service = createWorkService(req)
            def saved = servicesRepository.save(service)
            return decoratorPatternServices(saved)
        } catch (ResourceNotFoundException | NoSuchElementException ex) {
            throw new CreateResourceException(ex.message)
        }
    }

    //TODO: UPDATE

    protected static createWorkService(ServicesRequestDTO requestDTO) {
        return new WorkServices(
                service_name: requestDTO.service_name,
                barber_service: requestDTO.barber_service,
                hairdresser_service: requestDTO.hairdresser_service,
                promos: requestDTO.promos,
                price_service: requestDTO.price_service ?: calculatePrice( requestDTO.barber_service ,requestDTO.hairdresser_service),
                under_promotion: requestDTO?.under_promotion ?: false,
                total_cost: requestDTO.under_promotion ? calculateTotal(requestDTO.price_service, requestDTO.promos) : requestDTO.price_service,
                duration_of_service: requestDTO.duration_of_service,
                start: requestDTO.start,
                finish: LocalDateTime.from(requestDTO.start).plusMinutes(requestDTO.duration_of_service.time),
                username: requestDTO.user_id,
                client_id: requestDTO.client_id,
                client_name: requestDTO.client_name,
                created_on: Instant.now(),
                status: WorkServiceStatus.IN_PROGRESS
        )
    }

    protected static Double calculateTotal(price, promos) {
        /*
            TODO:
                Crear el servicio de Promociones o modificar el enum con valores correctos.
                promos deberian de ser promociones de servicios con descuentos por lo que se deberia de llamar 'Discounts'
                    Ej:     name = CORTE_BARBA , value = ' 10 % '
                            name = CORTE_CEJAS , value = ' 20 % '
         */

        def discount = price * promos.value / 100.0
        return price - discount


    }

    protected static Double calculatePrice(barberPrice, hairdresserPrice) {
        return barberPrice.value ?: hairdresserPrice.value
    }

    protected static ServicesResponseDTO decoratorPatternServices(WorkServices res) {
        new ServicesResponseDTO(
                id: res?.id,
                serviceName: res?.service_name,
                barberService: res?.barber_service,
                hairdresserService: res?.hairdresser_service,
                promos: res?.promos,
                priceService: res?.price_service,
                totalCost: res?.total_cost,
                underPromotion: res?.under_promotion,
                durationOfService: res?.duration_of_service,
                start: res?.start,
                finish: res?.finish,
                userId: res?.user_id,
                employeeUsername: res?.username,
                clientId: res?.client_id,
                clientName: res?.client_name,
                status: res?.status
        )
    }

}
