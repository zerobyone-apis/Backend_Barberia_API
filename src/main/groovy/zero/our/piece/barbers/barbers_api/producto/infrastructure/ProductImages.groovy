package zero.our.piece.barbers.barbers_api.producto.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "product_images")
class ProductImages {
    @Id
    Long id
    Long productId
    Long imageId
}
