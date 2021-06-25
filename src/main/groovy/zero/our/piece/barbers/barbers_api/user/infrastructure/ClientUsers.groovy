package zero.our.piece.barbers.barbers_api.user.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "client_users")
class ClientUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_users_sequence")
    @SequenceGenerator(name = "client_users_sequence", sequenceName = "client_users_sequence", allocationSize = 1)
    Long id
    Long clientId
    Long userId
}
