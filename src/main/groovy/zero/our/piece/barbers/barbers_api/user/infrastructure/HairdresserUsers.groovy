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
@Table(name = "hairdresser_users")
class HairdresserUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hairdresser_users_sequence")
    @SequenceGenerator(name = "hairdresser_users_sequence", sequenceName = "hairdresser_users_sequence", allocationSize = 1)
    Long id
    Long hairdresser_id
    Long userId
}
