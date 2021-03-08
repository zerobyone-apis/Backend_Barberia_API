package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@ToString
@EqualsAndHashCode
@Table(name="countries")
class Countries {

    @NotNull
    Long id

    String name

    String iso_code
}
