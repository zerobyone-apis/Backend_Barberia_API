package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise_reserves")
class EnterpriseReserves {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ent_reserves_sequence")
    @SequenceGenerator(name = "ent_reserves_sequence", sequenceName = "ent_reserves_sequence", allocationSize = 1)
    Long id
    Long enterprise_id
    Long reserve_id
}
