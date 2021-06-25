package zero.our.piece.barbers.barbers_api.services.model

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
@Table(name = "work_services")
class WorkServices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_services_sequence")
    @SequenceGenerator(name = "work_services_sequence", sequenceName = "work_services_sequence", allocationSize = 1)
    Long id
    String description

    // Type Service
    ServicesBarber barberService
    ServicesHairdresser hairdresserService

    // promotions
    Promos promos

    // Cost service
    Double priceService
    Double productCost                                  //TODO: Crear el servicio de producto y validar en el servicio para hacer analitycs
    Double externalServicesCost
    Double totalCost
    Boolean underPromotion

    // Time Service
    String duration
    LocalDateTime start                                       // Fecha y hora
    LocalDateTime finish                                      // Fecha y hora se puede calcular en base a la duration pero para persivir mejor el tiempo

    // user & client info
    Long userId
    String username
    Long clientId
    String clientName
    String clientPhone
    String clientEmail
    String socialNumber

    Instant createdOn
    Instant updatedOn
    Instant canceledOn

    WorkServiceStatus status
}

