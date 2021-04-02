package zero.our.piece.barbers.barbers_api.reserve.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.sql.Time

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "reserves")
class Reserves {

    @Id
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
    DateTime reserveDatetime // Las dos cosas, el dia y la hora.

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
    DateTime createdOn
    DateTime updateOn
    DateTime cancelOn

    WorkServiceStatus reserveStatus

    Boolean isActive = Boolean.TRUE // Esto deberia reemplazarse por el RESERVED




}
