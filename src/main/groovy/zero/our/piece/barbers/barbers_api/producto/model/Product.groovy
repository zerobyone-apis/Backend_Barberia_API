package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.Transient
import javax.validation.constraints.NotEmpty
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "products")
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence")
    @SequenceGenerator(name = "products_sequence", sequenceName = "products_sequence", allocationSize = 1)
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    Long brand_id
    Long price_id
    Long country_id
    Long provider_id
    Long category_id

    @Transient
    List<Image> images

    // Metrics data product
    @Transient
    Double amount_product_seller
    @Transient
    Double best_price_seller
    @Transient
    Double average_amount_per_product
    @Transient
    List<Map<String, Double>> provider_most_seller_product
    @Transient
    List<Map<String, Double>> life_time_product_in_shops
    @Transient
    List<Map<String, Double>> how_much_time_to_Renovate_product_by_shop

    Instant created_on
    Instant updated_on
    Instant deleted_on

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
