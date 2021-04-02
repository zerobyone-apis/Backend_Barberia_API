package zero.our.piece.barbers.barbers_api.proveedor.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.producto.model.Product

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

    Long brandId
    Long countryId

    @Transient
    List<Enterprise> enterprises

    @Transient
    List<Product> products

    DateTime createdOn
    DateTime updatedOn
    DateTime deletedOn
    Boolean enabled = Boolean.TRUE

}
