package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@ToString
@EqualsAndHashCode
@Entity
@Table(name = "days_to_work")
class DaysToWork {
    @Id
    Long id
    WeekDays dayName
    String openTime
    String closeTime
}