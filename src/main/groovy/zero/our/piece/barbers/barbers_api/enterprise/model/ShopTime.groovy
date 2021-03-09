package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Embeddable
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "shop_time")
class ShopTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    Long addressId
    Long EnterpriseId
    @Embedded
    @ElementCollection
    List<DaysToWork> workDays //todo: Probar si el toString() guarda el formato del arreglo -> VARCHAR()
}

class DaysToWork {
    WeekDays dayName
    String openTime
    String closeTime
}

enum WeekDays {
    LUNES("9:00 a 19:00"),
    MARTES("9:00 a 19:00"),
    MIERCOLES("9:00 a 19:00"),
    JUEVES("9:00 a 19:00"),
    VIERNES("9:00 a 19:00"),
    SABADO("9:00 a 19:00"),
    DOMINGO("9:00 a 14:00")
}

enum WeekTimes {
    //Lunes_Viernes(value = " ")
}