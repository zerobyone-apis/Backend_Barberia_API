package zero.our.piece.barbers.barbers_api.producto.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "product_price")
class ProductPrice {

    @Id
    Long id
    Long countryId
    Double price
    Double highlightedPrice

    Boolean isInOffert
    String nameOffert

    Double haveDiscountPercentage // porcentaje de descuente ej: 10%

    DateTime createdOn
    DateTime updatedOn
    Boolean enabled = Boolean.TRUE

    def getWithDiscountApplied(){}

}
