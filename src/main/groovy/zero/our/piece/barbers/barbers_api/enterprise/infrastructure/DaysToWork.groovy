package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "days_to_work")
class DaysToWork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "days_to_work_sequence")
    @SequenceGenerator(name = "days_to_work_sequence", sequenceName = "days_to_work_sequence", allocationSize = 1)
    Long id
    WeekDays day_name
    String open_time
    String close_time
}