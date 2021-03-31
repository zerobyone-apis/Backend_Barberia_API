package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "products")
class Product {

    @Id
    Long id
    Long brandId

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name


    Long country_id //todo: Revisar si puede ir bien con Long
    Long providerId //todo: Revisar si puede ir bien con Long
    Long categoryId //todo: Revisar si puede ir bien con Long

//    List<Image> images //TODO: SE RESUELVE HACIENDO UNA TABLA CON EL ID DEL PRODUCTO Y ID DE CADA UNO DE LOS IMAGENES QUE ESTA EMPRESA PROVEEA - Lista de Imagenes que la producto tiene

    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn

    Boolean enabled = Boolean.TRUE


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
