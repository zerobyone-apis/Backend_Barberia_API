package zero.our.piece.barbers.barbers_api.reserve.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import javax.persistence.*
import java.sql.Time
import java.time.Instant
import java.time.LocalDateTime

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "reserves")
@SequenceGenerator(name = "reserves_sequence", sequenceName = "reserves_sequence", allocationSize = 1)
class Reserves {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserves_sequence")
    Long id

    // user - barber or hairdresser info
    Long userId
    Long enterpriseId
    String employeeUsername // El barbero o peluquero con el que se agenda.

    // client info
    Long clientId

    // DateTime info
    String duration
    LocalDateTime reserveDatetime       // Las dos cosas, el dia y la hora.

    // Reserve Description Info
    Long workServiceId

    // Creation data
    Instant createdOn
    Instant updateOn
    Instant cancelOn

    Boolean requestedCancel              //todo: Propiedad por si el cliente quiere cancelar la reserva

    Boolean isActive = Boolean.TRUE


}
