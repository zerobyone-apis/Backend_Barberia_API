package zero.our.piece.barbers.barbers_api.proveedor.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

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
