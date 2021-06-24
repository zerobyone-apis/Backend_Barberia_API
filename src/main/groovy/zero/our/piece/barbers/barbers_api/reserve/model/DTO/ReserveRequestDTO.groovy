
package zero.our.piece.barbers.barbers_api.reserve.model.DTO

import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser

import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

class ReserveRequestDTO {

    // user - barber or hairdresser info
    Long userId
    Long enterpriseId
    String employeeUsername // El barbero o peluquero con el que se agenda.

    // client info
    Long clientId
    Long clientName
    String clientPhone
    String emailClient
    String socialNumber

    // DateTime info
    String duration               // hh:mm
    LocalDateTime reserveDatetime // Las dos cosas, el dia y la hora.

    // Reserve Description Info
    Long workServiceId
    ServicesBarber barberService
    ServicesHairdresser hairdresserService
    Promos promos

    // Cost reserve - Los mismos que el servicio pero se añaden los del producto
    Double priceService
    Boolean underPromotion
    Double productCost
    Double externalServicesCost // Cafeteria, bebidas, etc,

}
