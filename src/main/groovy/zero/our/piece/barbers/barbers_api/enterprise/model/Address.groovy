package zero.our.piece.barbers.barbers_api.enterprise.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "address")
class Address {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long countryId
    Long placeId
    // example: referencia de lugar, zona o barrio, crear a futuro un listado con las zonas conocidas para poder comparar.
    String additionalInfo
    String postalCode

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
