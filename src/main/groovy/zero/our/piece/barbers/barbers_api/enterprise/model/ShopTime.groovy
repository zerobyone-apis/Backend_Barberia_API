package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.type.TrueFalseType
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.WeekDays

import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import java.time.Instant

//@Embeddable
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "shop_time")
class ShopTime {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long addressId
    Long enterpriseId

   // List<DaysToWork> workDays //todo: Tabla con id y lista de dias to work o ver la forma luego
    DateTime createdOn
    DateTime deletedOn
    Boolean enabled = Boolean.TRUE

}

class DaysToWork {
    WeekDays dayName
    String openTime
    String closeTime
}