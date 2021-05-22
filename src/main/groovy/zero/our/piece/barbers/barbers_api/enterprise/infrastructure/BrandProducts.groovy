package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "brand_products")
class BrandProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_products_sequence")
    @SequenceGenerator(name = "brand_products_sequence", sequenceName = "brand_products_sequence", allocationSize = 1)
    Long id
    Long brand_id
    Long product_id
}
