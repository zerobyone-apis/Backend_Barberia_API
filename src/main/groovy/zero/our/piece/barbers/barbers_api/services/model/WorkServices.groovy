package zero.our.piece.barbers.barbers_api.services.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import java.sql.Time
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "work_services")
class WorkServices {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_services_sequence")
    @SequenceGenerator(name = "work_services_sequence", sequenceName = "work_services_sequence", allocationSize = 1)
    Long id
    String service_name

    // Type Service
    ServicesBarber barber_service
    ServicesHairdresser hairdresser_service
    Promos promos

    // Cost service
    Double price_service
    Double total_cost        //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
    Boolean under_promotion  //todo: Se aplica alguna promocion?

    // Time Service
    Time duration_of_service //todo: esto puede ser un enum con los horarios prestablecidos de tiempo
    Instant start //fecha y hora
    Instant end   //fecha y hora se puede calcular en base a la duration pero para persivir mejor el tiempo

    // user & client info
    Long user_id
    String username
    Long client_id
    String client_name

    Instant created_on
    Instant updated_on

    WorkServiceStatus status
}

