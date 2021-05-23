package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise_products")
class EnterpriseProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_products_sequence")
    @SequenceGenerator(name = "enterprise_products_sequence", sequenceName = "enterprise_products_sequence", allocationSize = 1)
    Long id
    Long enterprise_id
    Long product_id
}
