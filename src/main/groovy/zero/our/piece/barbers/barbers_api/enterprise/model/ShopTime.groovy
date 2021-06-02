package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.DaysToWork

import javax.persistence.*
import java.time.Instant

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "shop_time")
@SequenceGenerator(name = "shop_time_sequence", sequenceName = "shop_time_sequence", allocationSize = 1)
class ShopTime {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_time_sequence")
    Long id
    Long address_id
    Long enterprise_id

    /** TODO: Se podria detallar los dias feriados o que no se trabajen o directamente que el local cierre
     *        la idea es que puedan manejar to_do lo relacionado a el horario, asi como timezone, y actualizaciones de horarios
     *        dias libres o feriados.
     **/

    @Transient
    List<DaysToWork> workDays

    Instant created_on
    Instant deleted_on
    Boolean enabled = Boolean.TRUE

}