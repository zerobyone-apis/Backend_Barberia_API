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
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name="enterprise")
class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @NotEmpty(message = "INVALID_LEGAL_NAME")
    @Length(min = 3, max = 200)
    String legalName

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String internalPhone

    @NotNull(message = "INVALID_LEGAL_ID")
    @Length(min = 1, max = 200)
    String legalNumber

    @NotNull(message = "INVALID_BRAND")
    Brand brand

    ShopTime openTime
    ServicesProvider services //Lista de servicios que la empresa cliente prove
    EnterpriseStatus enterpriseStatus
    Address address
    List<Proveedor> proveedores
    List<Product> products
    List<Users> users
    RatingScore rating
    Instant createdOn
    Instant updatedOn

    Boolean hasIntegration


    Boolean enabled = Boolean.TRUE

    //TODO: Queda temrinar varios de las entidades y realizar todas las tablas en liquibase.
    // See: https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
}