package zero.our.piece.barbers.barbers_api.proveedor.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "provider_enterprise")
class ProviderEnterprise {

    @Id
    Long id
    Long providerId
    Long enterpriseId
}
