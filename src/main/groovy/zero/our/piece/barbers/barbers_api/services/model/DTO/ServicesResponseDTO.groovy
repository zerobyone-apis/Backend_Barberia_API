package zero.our.piece.barbers.barbers_api.services.model.DTO

import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

class ServicesResponseDTO {

    Long id
    String serviceName  // FIXME: de momento puede servir para añadir mas info descriptivo al servicio pero podriamos renombrar

    // Type Service
    ServicesBarber barberService
    ServicesHairdresser hairdresserService
    Promos promos

    // Cost service
    Double priceService
    Double totalCost
    Boolean underPromotion

    // Time Service
    Time durationOfService
    LocalDateTime start
    LocalDateTime finish

    // user & client info
    Long userId
    String employeeUsername

    Long clientId
    String clientName

    WorkServiceStatus status
}
