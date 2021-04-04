package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.EnterpriseStatus
import zero.our.piece.barbers.barbers_api.producto.model.Product
import zero.our.piece.barbers.barbers_api.proveedor.model.Provider
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves
import zero.our.piece.barbers.barbers_api.user.model.User

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.Transient
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise")
@SequenceGenerator(name = "enterprise_sequence", sequenceName = "enterprise_sequence", allocationSize = 1)
class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_sequence")
    Long id

    // Enterprise info
    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @NotEmpty(message = "INVALID_LEGAL_NAME")
    @Length(min = 3, max = 200)
    String legal_name

    @NotEmpty(message = "INVALID_EMAIL")
    @Length(min = 6, max = 200)
    String email

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String internal_phone

    @NotNull(message = "INVALID_LEGAL_ID")
    @Length(min = 1, max = 200)
    String legal_number

    @NotNull(message = "INVALID_BRAND")
    Long brand_id

    @NotNull(message = "INVALID_OPEN_TIMES")
    Long shop_time_id

    @NotNull(message = "INVALID_ADDRESS_ID")
    Long address_id

    Long rating_score_id
    EnterpriseStatus status

    // Transient attributes
    @Transient
    List<Reserves> reserves  // fillear este campo cuando se busque a la empresa, consultando las respectivas tablas por id

    @Transient
    List<Provider> providers // fillear este campo cuando se busque a la empresa, consultando las respectivas tablas por id

    @Transient
    List<Product> products  // fillear este campo cuando se busque a la empresa, consultando las respectivas tablas por id

    @Transient
    List<User> users // fillear este campo cuando se busque a la empresa, consultando las respectivas tablas por id

    Instant created_on
    Instant updated_on

    Boolean has_integration // Integration with others apis
    Boolean enabled = Boolean.TRUE

    //TODO: Queda temrinar varios de las entidades y realizar todas las tablas en liquibase.
    // See: https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
}
