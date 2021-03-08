package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@EqualsAndHashCode
@ToString
@Table(name = "address")
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    @Column
    String formmatted

    @Column
    String postalCode

    @Column
    String placeId

    @Column
    Countries state

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    @Column
    BigDecimal lat

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    @Column
    BigDecimal lon
    String additionalInfo

    @Column
    Boolean enabled

    Coordinates getCoordinates() {
        new Coordinates(lat: lat, lng: lon)
    }
}
