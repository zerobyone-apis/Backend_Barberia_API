
package zero.our.piece.barbers.barbers_api.services.model.DTO

import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

class ServicesRequestDTO {

     String description

    // Type Service
    ServicesBarber barberService
    ServicesHairdresser hairdresserService
    Promos promos

    // Cost service
    Double priceService
    Double productCost
    Double externalServicesCost
    Double totalCost
    Boolean underPromotion

    // Time Service
    String duration
    LocalDateTime start

    // user & client info
    Long userId
    String username

    Long clientId
    String clientName
    String clientPhone
    String clientEmail
    String socialNumber

}
