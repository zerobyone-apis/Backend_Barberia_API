package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.producto.model.Product
import zero.our.piece.barbers.barbers_api.proveedor.model.Proveedor
import zero.our.piece.barbers.barbers_api.user.model.Users

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import java.sql.Timestamp

@Entity
@ToString
@EqualsAndHashCode
@Table(name="brand")
class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "INVALID_BRAND_NAME")
    @Length(min = 3, max = 200)
    String name

    String title
    String slogan

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String preferredPhone

    @NotNull(message = "INVALID_AVATAR")
    String avatar

    @NotNull(message = "INVALID_BANNER")
    String banner

    String urlFolderImages
    Timestamp createdOn
    Timestamp updatedOn
    Timestamp deletedOn
    Boolean enabled = Boolean.TRUE

}
