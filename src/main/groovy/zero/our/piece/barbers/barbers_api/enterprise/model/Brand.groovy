package zero.our.piece.barbers.barbers_api.enterprise.model

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
@Table(name = "brand")
class Brand {

    @Id
    Long id
    Long countryId

    @NotEmpty(message = "INVALID_BRAND_NAME")
    @Length(min = 3, max = 200)
    String name

    String title
    String slogan

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String preferredPhone
    //TODO: This is the number to show on the image on front or the marketing campaign

    // List<Image> images //TODO: Crear tabla de IDs conteniendo las imagenes que esta marca tenga, idBrand, idImages (idBrand= 1, idImage = 1) (idBrand= 1, idImage = 2) (idBrand= 1, idImage = 3)
    // List<Product> products //TODO: Crear tabla de IDs conteniendo las productos que esta marca tenga, idBrand, idProducto (idBrand= 1, idProducto = 1) (idBrand= 1, idImage = 2) (idBrand= 1, idImage = 3)
    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn
    Boolean enabled = Boolean.TRUE

}
