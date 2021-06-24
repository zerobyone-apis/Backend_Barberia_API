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
/*
    TODO:
     Hacer un refactor en el PASCAL_CASE a CamelCase -> Tablas, Modelos, y DTOS, lugares donde se esten llamando.
 */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_services_sequence")
    @SequenceGenerator(name = "work_services_sequence", sequenceName = "work_services_sequence", allocationSize = 1)
    Long id
    String service_name                                 // FIXME: de momento puede servir para añadir mas info descriptivo al servicio pero podriamos renombrar

    // Type Service
    ServicesBarber barber_service
    ServicesHairdresser hairdresser_service
    Promos promos

    // Cost service
    Double price_service
    Double total_cost                                   //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
    Boolean under_promotion                             //todo: Se aplica alguna promocion?

    /*
        TODO:
         Falta la info de Producto que se añade en Reserves Table.
         pasarlo para este entity
     */

    // Time Service
    Time duration_of_service                                  // Esto puede ser un enum con los horarios prestablecidos de tiempo
    LocalDateTime start                                       // Fecha y hora
    LocalDateTime finish                                      // Fecha y hora se puede calcular en base a la duration pero para persivir mejor el tiempo

    // user & client info
    Long user_id
    String username
    Long client_id
    String client_name

    Instant created_on
    Instant updated_on

    WorkServiceStatus status
}

