package zero.our.piece.barbers.barbers_api.user.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles
import zero.our.piece.barbers.barbers_api.user.service.Action

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "register_logins")
class RegisterLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_logins_sequence")
    @SequenceGenerator(name = "register_logins_sequence", sequenceName = "register_logins_sequence", allocationSize = 1)
    Long id
    String username
    String email
    Long social_number   //todo: sencive data we need to encrypt

    Long barber_id

    Long hairdresser_id

    Long client_id

    Action action
    Instant init_session
    Instant end_session
    String roles        //todo: sencive data we need to encrypt


}

