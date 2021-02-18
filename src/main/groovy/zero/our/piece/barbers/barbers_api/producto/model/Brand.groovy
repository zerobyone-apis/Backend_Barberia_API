package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name="brand")
class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id

    @Column
    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 2, max = 200)
    String name

    @Column
    Boolean enabled = Boolean.TRUE

    @Column
    String logo

    @Column
    List<Product> products

    @Column
    String country_id
}
