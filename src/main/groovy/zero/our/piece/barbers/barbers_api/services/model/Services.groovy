package zero.our.piece.barbers.barbers_api.services.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.producto.model.Image

import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Table
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name="services")
@Embeddable
class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ElementCollection(targetClass = WorkServices.class)
    @Embedded
    @CollectionTable(name = "images")
    List<WorkServices> services


    Instant createdOn
    Instant updateOn

    Boolean enabled = Boolean.TRUE

}

class WorkServices {
    ServicesBarber servicesBarber
    ServicesHairdresser servicesHairdresser
    Promos promos
}

/**********  BARBERIA   ***************/
enum ServicesBarber {
    FADE,                       // 270
    CLASICO,                    // 230
    BARBA,                      // 120
    CEJA,                       // 70
    AFEITADO_CLASICO,           // 160
    BRUSHING_PROGRESIVO_BARBER, // desde 500
    MECHAS_BARBER,              // Desde 500
    PLANCHADO,                  // 1200
    COLORES_FANTASIA_BARBER     // 800
}

/**********   PELUQUERIA  *************/
enum ServicesHairdresser {
    CORTE,               // 250
    LAVADO,              // 50
    BRUSHING,            // Desde_200
    DEPILACION,          // (ceja, bozo, menton) 70c/u
    BOTOX,               // 690
    HIDROCAUTERIZACION,  // 590
    BRUSHING_PROGRESIVO, // desde 1200
    CLARITOS,            // 900
    MECHAS,              // 900
    REFLEJOS,            // 900
    BALAYAGE,            // desde 1600
    CALIFORNIANAS,       // desde 1600
    COLOR,               // 600
    COLORES_FANTASIA,    // 600
    APLICACION,          // 280
    TRENZAS              // 200
}

/***********  PROMOS  *************/
enum Promos {
    CORTE_BARBA,        // 350
    CORTE_BARBA_CEJAS,  // 400
    CORTE_CEJAS,        // 320
    CORTE_BLACKMASK     // 500
}
