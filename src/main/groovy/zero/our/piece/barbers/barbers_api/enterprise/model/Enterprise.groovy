package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.producto.model.Product
import zero.our.piece.barbers.barbers_api.proveedor.model.Proveedor

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
@Table(name="enterprise")
class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column
    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @Column
    @NotEmpty(message = "INVALID_LEGAL_NAME")
    @Length(min = 3, max = 200)
    String legalName

    @Column
    @NotNull(message = "INVALID_AVATAR")
    String avatar

    @Column
    Boolean enabled = Boolean.TRUE

    @Column
    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String phone

    @Column
    @NotNull(message = "INVALID_LEGAL_ID")
    @Length(min = 1, max = 200)
    String legalNumber

    @Column
    EnterpriseStatus enterpriseStatus

    @Column
    String address

    @Column
    String postalCode

    @Column
    List<Proveedor> proveedores

    @Column
    List<Product> products

    @Column
    RatingScore rating

    @Column
    Boolean hasIntegration

    @Column
    @NotNull(message = "INVALID_COUNTRY_ID")
    String country_id

    //TODO: Queda temrinar varios de las entidades y realizar todas las tablas en liquibase.
    // See: https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
}
