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
    String employeeUsername // El barbero o peluquero con el que se agenda. v

    // client info
    Long clientId
    Long clientName
    String clientPhone
    String emailClient
    String socialNumber

    // DateTime info
    Time duration
    Instant reserveDatetime // Las dos cosas, el dia y la hora.

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
    Double totalCost //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.

    // Creation data
    Instant createdOn
    Instant updateOn
    Instant cancelOn

    WorkServiceStatus reserveStatus

    Boolean isActive = Boolean.TRUE // Esto deberia reemplazarse por el RESERVED


}
