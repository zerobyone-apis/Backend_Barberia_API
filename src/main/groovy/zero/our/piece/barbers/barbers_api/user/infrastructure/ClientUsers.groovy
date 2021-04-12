package zero.our.piece.barbers.barbers_api.user.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "client_users")
class ClientUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_users_sequence")
    @SequenceGenerator(name = "client_users_sequence", sequenceName = "client_users_sequence", allocationSize = 1)
    Long id
    Long client_id
    Long user_id
}
