package zero.our.piece.barbers.barbers_api.proveedor.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.producto.model.Product

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "providers")
class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "providers_sequence")
    @SequenceGenerator(name = "providers_sequence", sequenceName = "providers_sequence", allocationSize = 1)
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

    Instant createdOn
    Instant updatedOn
    Instant deletedOn
    Boolean enabled = Boolean.TRUE

}
