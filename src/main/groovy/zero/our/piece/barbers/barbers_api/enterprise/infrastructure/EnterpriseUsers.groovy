package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise_users")
class EnterpriseUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enterprise_users_sequence")
    @SequenceGenerator(name = "enterprise_users_sequence", sequenceName = "enterprise_users_sequence", allocationSize = 1)
    Long id
    Long enterprise_id
    Long user_id
    Long social_number
}
