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
    Long clientName                             //fixme: Unnecessary by client ID
    String clientPhone                          //fixme: Unnecessary by client ID
    String emailClient                          //fixme: Unnecessary by client ID
    String socialNumber                         //fixme: Unnecessary by client ID

    // DateTime info
    Time duration
    LocalDateTime reserveDatetime                     // Las dos cosas, el dia y la hora.

    // Reserve Description Info
    Long workServiceId
    ServicesBarber barberService                //fixme: Unnecessary by workService ID
    ServicesHairdresser hairdresserService      //fixme: Unnecessary by workService ID
    Promos promos                               //fixme: Unnecessary by workService ID

    // Cost reserve - Los mismos que el servicio pero se añaden los del producto
    Double priceService                          //fixme: Unnecessary by workService ID
    Boolean underPromotion                       //fixme: Unnecessary by workService ID
    Double productCost                           //TODO: Crear el servicio de producto y validar en el servicio para hacer analitycs
    Double externalServicesCost                  // Cafeteria, bebidas, etc,
    Double totalCost                            //fixme: Unnecessary by workService ID

    // Creation data
    Instant createdOn
    Instant updateOn
    Instant cancelOn

    WorkServiceStatus reserveStatus              //fixme: Unnecessary by workService ID

    Boolean isActive = Boolean.TRUE             //todo: Esto deberia reemplazarse por el RESERVED


}
