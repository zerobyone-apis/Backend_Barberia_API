package zero.our.piece.barbers.barbers_api.user.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "barber_users")
class BarberUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barber_users_sequence")
    @SequenceGenerator(name = "barber_users_sequence", sequenceName = "barber_users_sequence", allocationSize = 1)
    Long id
    Long barberId
    Long userId
}
