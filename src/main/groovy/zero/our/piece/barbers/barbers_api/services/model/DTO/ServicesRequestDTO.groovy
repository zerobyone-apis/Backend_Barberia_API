
package zero.our.piece.barbers.barbers_api.services.model.DTO

import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

class ServicesRequestDTO {

     String service_name

    // Type Service
    ServicesBarber barber_service
    ServicesHairdresser hairdresser_service
    Promos promos

    // Cost service
    Double price_service
    Double total_cost
    Boolean under_promotion

    // Time Service
    Time duration_of_service
    LocalDateTime start

    // user & client info
    Long user_id
    String username

    Long client_id
    String client_name

}
