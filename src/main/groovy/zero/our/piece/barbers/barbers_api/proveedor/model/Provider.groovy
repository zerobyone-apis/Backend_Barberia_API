package zero.our.piece.barbers.barbers_api.proveedor.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.producto.model.Category

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name="providers")
@Embeddable
class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @Embedded
    @ElementCollection
    Category category
    Boolean enabled = Boolean.TRUE

}
