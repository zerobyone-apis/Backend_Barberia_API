package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Table

@ToString
@EqualsAndHashCode
@Entity
@Table(name="shop_time")
class ShopTime {

    Long id
    Long addressId
    Long EnterpriseId
    List<DaysToWork> workDays


}

class DaysToWork {
    String dayName
    String openTime
    String closeTime
}

enum WeekDays {
    LUNES ("9:00 a 19:00"),
    MARTES ("9:00 a 19:00"),
    MIERCOLES ("9:00 a 19:00"),
    JUEVES ("9:00 a 19:00"),
    VIERNES ("9:00 a 19:00"),
    SABADO ("9:00 a 19:00"),
    DOMINGO ("9:00 a 14:00")
}

enum WeekTimes {
    //Lunes_Viernes(value = " ")
}