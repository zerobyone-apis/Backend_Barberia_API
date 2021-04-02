package zero.our.piece.barbers.barbers_api.producto.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "category")
class Category {

    @Id
    Long id
    Long parentId
    Long countryId
    String name
    Boolean enabled
    Boolean isLeaf

    @Transient
    List<Category> childrens
                                // todo: lista de tienda, o sub tiendas o categorias en si como sevicios y dentro de esos servicios sub servicios
                                // todo: ejemplo : Barberia: id 1,   nombre = art, parent = null, children = [2,3,4]
                                //                Barberia_2: id 2, nombre = art, parent = 1   , children = []
                                //                Barberia_3: id 3, nombre = art, parent = 1   , children = []
                                //                Barberia_4: id 4, nombre = art, parent = 1   , children = []
}
