package zero.our.piece.barbers.barbers_api.enterprise.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.math.RoundingMode

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "rating_score")
class RatingScore {

    @Id
    Long id
    Long percentage
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
