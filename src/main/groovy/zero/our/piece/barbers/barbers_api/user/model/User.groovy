package zero.our.piece.barbers.barbers_api.user.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.Type
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    Long id
    Long enterprise_id // creo que esto se puede ir, y guardar directamente en la tabla de (ID USERS - ID ENTERPRISE)
    Long barber_id
    Long hairdresser_id
    Long client_id
    String username     //todo: sencive data we need to encrypt
    String password     //todo: sencive data we need to encrypt
    String email        //todo: sencive data we need to encrypt
    Long social_number   //todo: sencive data we need to encrypt
    Instant created_on
    Instant update_on
    UsersPermission permission
    Boolean is_active = Boolean.TRUE
}

