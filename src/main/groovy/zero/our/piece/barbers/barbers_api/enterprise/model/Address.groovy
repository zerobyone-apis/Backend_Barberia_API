package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "address")
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    Long id
    Long country_id
    Long place_id // example: referencia de lugar, zona o barrio, crear a futuro un listado con las zonas conocidas para poder comparar.
    String additional_info
    String postal_code

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    String formatted

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lat

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lon

    Instant created_on
    Instant updated_on
    Instant deleted_on

    Boolean enabled = Boolean.TRUE

    Coordinates getCoordinates() {
        new Coordinates(lat: lat, lng: lon)
    }
}
