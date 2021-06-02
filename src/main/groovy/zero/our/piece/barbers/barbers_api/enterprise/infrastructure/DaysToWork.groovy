package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "days_to_work")
@SequenceGenerator(name = "days_to_work_sequence", sequenceName = "days_to_work_sequence", allocationSize = 1)
class DaysToWork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "days_to_work_sequence")
    Long id
    Long shop_time_id
    WeekDays day_name
    String open_time
    String close_time
}