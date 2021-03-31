package zero.our.piece.barbers.barbers_api.producto.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "category_childrens")
class CategoryChildrens {
    @Id
    Long id
    Long categoryId
    Long parentId
    String name
}
