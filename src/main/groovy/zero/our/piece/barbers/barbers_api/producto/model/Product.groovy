package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "products")
class Product {

    @Id
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    Long brandId
    Long priceId
    Long countryId
    Long providerId
    Long categoryId

    @Transient
    List<Image> images

    // Metrics data product
    @Transient
    Double amountProductSeller
    @Transient
    Double bestPriceSeller
    @Transient
    Double averageAmountPerProduct
    @Transient
    List<Map<String,Double>> providerMostSellerProduct
    @Transient
    List<Map<String,Double>> lifeTimeProductInShops
    @Transient
    List<Map<String,Double>> howMuchTimeToRenovateProductByShop

    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn

    Boolean enabled = Boolean.TRUE

    /*
    // todo: tener en cuenta a futuro de hacer un listado de precios modificable
            con grandes cualidades al POR MAYOR Y AL POR MENOR PARA TIENDAS Y PROVEEDORES.

    @JsonProperty("priceFrom")
    Price getPriceFrom() {
        def p = getPrices()
        if (p && !p.isEmpty()) {
            return p.min { it.unitValue }
        }
        null
    }

    @JsonProperty("highlightedPrice")
    Price getHighlightedPrice() {
        highlightedPrice ?: getPriceFrom()
    }

    @JsonProperty("minUnitsPrice")
    Price getMinUnitsPrice() {
        def p = getPrices()
        if (p && !p.isEmpty()) {
            Price minPriceUnits = p.min { it.minUnits }
            List<Price> shared = p.findAll { it.minUnits == minPriceUnits.minUnits }
            return shared.min { it.unitValue }
        }
        null
    }
     */
}
