package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise_products")
class EnterpriseProducts {

    @Id
    Long id
    Long enterpriseId
    Long productId
}