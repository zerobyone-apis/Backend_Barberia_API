package zero.our.piece.barbers.barbers_api.proveedor.infrastructure

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
@Table(name = "provider_enterprise")
class ProviderEnterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_enterprise_sequence")
    @SequenceGenerator(name = "provider_enterprise_sequence", sequenceName = "provider_enterprise_sequence", allocationSize = 1)
    Long id
    Long provider_id
    Long enterprise_id
}
