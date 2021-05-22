package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise_providers")
class EnterpriseProviders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_providers_sequence")
    @SequenceGenerator(name = "enterprise_providers_sequence", sequenceName = "enterprise_providers_sequence", allocationSize = 1)
    Long id
    Long enterprise_id
    Long provider_id
}
