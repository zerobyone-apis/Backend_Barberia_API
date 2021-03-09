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
@Table(name="category")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id

    @Column
    Long parentId

    @Column
    String name

    @Column
    Boolean enabled

    @Column
    Boolean isLeaf

    @Column
    String countryId

    @Column
    List<Category> children

}
