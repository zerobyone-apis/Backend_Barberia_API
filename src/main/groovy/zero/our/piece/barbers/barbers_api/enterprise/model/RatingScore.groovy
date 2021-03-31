package zero.our.piece.barbers.barbers_api.enterprise.model

import com.fasterxml.jackson.annotation.JsonProperty

import java.math.RoundingMode

class RatingScore {

    BigDecimal average

    Integer count

    @JsonProperty("percentage")
    Long getPercentage() {
        return (getAverage() / 5 * 100)?.setScale(2, RoundingMode.HALF_EVEN) ?: BigDecimal.ZERO
    }

    @JsonProperty("average")
    BigDecimal getAverage() {
        average?.setScale(2, RoundingMode.HALF_EVEN) ?: BigDecimal.ZERO
    }
}
