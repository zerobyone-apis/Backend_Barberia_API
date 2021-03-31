package zero.our.piece.barbers.barbers_api.proveedor.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import org.joda.time.DateTime

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name="providers")
class Provider{

    @Id
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @NotEmpty(message = "INVALID_EMAIL")
    @Length(min = 6, max = 200)
    String email

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String internalPhone

    Long categoryId //todo: probar si va con Long -> me refiero a si conviene string o long
    Long brandId
    Long countryId

    // List<Customers> tiendas *//todo: crear tabal de customer asociados a los que entrega.

    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn
    Boolean enabled = Boolean.TRUE

}
