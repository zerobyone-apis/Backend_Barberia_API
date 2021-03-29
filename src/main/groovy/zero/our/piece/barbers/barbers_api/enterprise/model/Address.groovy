package zero.our.piece.barbers.barbers_api.enterprise.model

import com.google.type.DateTime
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "address")
class Address {

    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long countryId
    String postalCode
    String placeId // example: referencia de lugar, zona o barrio, crear a futuro un listado con las zonas conocidas para poder comparar.
    String additionalInfo

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    String formatted

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lat

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lon

    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn

    Boolean enabled = Boolean.TRUE

    Coordinates getCoordinates() {
        new Coordinates(lat: lat, lng: lon)
    }
}
