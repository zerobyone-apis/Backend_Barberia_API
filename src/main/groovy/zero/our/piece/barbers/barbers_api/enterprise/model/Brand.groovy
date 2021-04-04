package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.producto.model.Image
import zero.our.piece.barbers.barbers_api.producto.model.Product

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
@Table(name = "brand")
class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    @SequenceGenerator(name = "brand_sequence", sequenceName = "brand_sequence", allocationSize = 1)
    Long id
    Long country_id

    @NotEmpty(message = "INVALID_BRAND_NAME")
    @Length(min = 3, max = 200)
    String name

    String title
    String slogan

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String preferred_phone  //TODO: This is the number to show on the image on front or the marketing campaign

    @Transient
    List<Image> images

    @Transient              //TODO: Crear tabla de IDs conteniendo las imagenes que esta marca tenga, idBrand, idImages (idBrand= 1, idImage = 1) (idBrand= 1, idImage = 2) (idBrand= 1, idImage = 3)
    List<Product> products  //TODO: Crear tabla de IDs conteniendo las productos que esta marca tenga, idBrand, idProducto (idBrand= 1, idProducto = 1) (idBrand= 1, idImage = 2) (idBrand= 1, idImage = 3)

    Instant created_on
    Instant updated_on
    Instant deleted_on
    Boolean enabled = Boolean.TRUE

}
