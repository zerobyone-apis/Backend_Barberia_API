package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "category")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    Long id
    Long parent_id
    Long country_id
    String name
    Boolean enabled
    Boolean is_leaf

    @Transient
    List<Category> childrens
                                // todo: lista de tienda, o sub tiendas o categorias en si como sevicios y dentro de esos servicios sub servicios
                                // todo: ejemplo : Barberia: id 1,   nombre = art, parent = null, children = [2,3,4]
                                //                Barberia_2: id 2, nombre = art, parent = 1   , children = []
                                //                Barberia_3: id 3, nombre = art, parent = 1   , children = []
                                //                Barberia_4: id 4, nombre = art, parent = 1   , children = []
}
