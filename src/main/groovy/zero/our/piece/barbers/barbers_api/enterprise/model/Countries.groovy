package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "countries")
class Countries {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_sequence")
    @SequenceGenerator(name = "countries_sequence", sequenceName = "countries_sequence", allocationSize = 1)
    Long id
    String name
    String iso_code
}
