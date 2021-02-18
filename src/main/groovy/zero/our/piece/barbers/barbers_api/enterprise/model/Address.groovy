package zero.our.piece.barbers.barbers_api.enterprise.model

import javax.validation.constraints.NotNull

class Address {

    Long id

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    String formmatted
    String postalCode
    String placeId
    State state

    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lat
    @NotNull(message = "INVALID_GOOGLE_ADDRESS")
    BigDecimal lon
    String additionalInfo
    Boolean enabled

    Coordinates getCoordinates() {
        new Coordinates(lat: lat, lng: lon)
    }
}
