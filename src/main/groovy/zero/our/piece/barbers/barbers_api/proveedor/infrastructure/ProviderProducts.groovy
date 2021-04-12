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
@Table(name = "provider_products")
class ProviderProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_products_sequence")
    @SequenceGenerator(name = "provider_products_sequence", sequenceName = "provider_products_sequence", allocationSize = 1)
    Long id
    Long provider_id
    Long product_id
}
