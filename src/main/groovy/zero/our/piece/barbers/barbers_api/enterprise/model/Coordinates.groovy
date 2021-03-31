package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@ToString
@EqualsAndHashCode
class Coordinates
{
    @NotNull(message = "INVALID_COORDINATE_LAT")
    @Min(value = -90L)
    @Max(value = 90L)
    Double lat

    @NotNull(message = "INVALID_COORDINATE_LNG")
    @Min(value = -180L)
    @Max(value = 180L)
    Double lng
}
