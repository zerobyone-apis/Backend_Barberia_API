package zero.our.piece.barbers.barbers_api.user.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.Type
import zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserPermission
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles

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
    String username
    String password     //todo: sencive data we need to encrypt
    String email
    Long social_number
    Instant created_on
    Instant update_on

    @Enumerated(EnumType.STRING)
    UsersRoles roles

    @Deprecated
    //@Enumerated(EnumType.STRING)
    ApplicationUserPermission permission   // todo: ver si se guardan los roles, o si se borra la columna

    Boolean is_active = Boolean.FALSE  //Ahora debera actualizarse por medio de notificacion en email.
}

