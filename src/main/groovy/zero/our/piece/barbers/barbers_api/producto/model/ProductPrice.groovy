package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "product_price")
class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_price_sequence")
    @SequenceGenerator(name = "product_price_sequence", sequenceName = "product_price_sequence", allocationSize = 1)
    Long id
    Long country_id
    Double price
    Double highlighted_price

    Boolean is_in_offert
    String name_offert

    Double have_discount_percentage // porcentaje de descuente ej: 10%

    Instant created_on
    Instant updated_on
    Boolean enabled = Boolean.TRUE

    def getWithDiscountApplied() {}

}
