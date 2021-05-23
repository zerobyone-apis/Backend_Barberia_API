package zero.our.piece.barbers.barbers_api.producto.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "product_images")
class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_images_sequence")
    @SequenceGenerator(name = "product_images_sequence", sequenceName = "product_images_sequence", allocationSize = 1)
    Long id
    Long productId
    Long imageId
}
