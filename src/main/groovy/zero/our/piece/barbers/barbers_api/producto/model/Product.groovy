package zero.our.piece.barbers.barbers_api.producto.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.proveedor.model.Provider

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@ToString
@EqualsAndHashCode
@Table(name="products")
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    @Column
    String name

    @Column
    Boolean enabled = Boolean.TRUE

    @Column
    Category category

    @NotNull(message = "INVALID_BRAND")
    @Column
    Brand brand

    @Column
    List<Image> images

    @Column
    Date created

    @Column
    String country_id

    @Column
    Provider proveedor


    //TODO: Queda temrinar varios de las entidades y realizar todas las tablas en liquibase.
    /*
    Price highlightedPrice


    //@JsonIgnore
    //List<Price> prices

    @NotNull(message = "INVALID_DISPLAYS")
    List<Display> displays

    @JsonProperty(value = "title")
    String getTitle() {
        name
    }


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
