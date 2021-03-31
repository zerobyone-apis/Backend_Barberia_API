package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "countries")
class Countries {
    @Id
    Long id
    String name
    String iso_code
}
