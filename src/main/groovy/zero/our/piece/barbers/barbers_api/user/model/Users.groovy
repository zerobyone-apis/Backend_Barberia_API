package zero.our.piece.barbers.barbers_api.user.model

import com.sun.istack.Nullable
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name="users")
class Users {

    @Id
    Long id
    Long enterpriseId // creo que esto se puede ir, y guardar directamente en la tabla de (ID USERS - ID ENTERPRISE)

    @Nullable
    Long barberId

    @Nullable
    Long hairdresserId

    @Nullable
    Long clientId

    String username     //todo: sencive data we need to encrypt
    String password     //todo: sencive data we need to encrypt
    String email        //todo: sencive data we need to encrypt
    String socialNumber //todo: sencive data we need to encrypt

    DateTime createdOn
    DateTime updateOn

    UsersPermission permission
    Boolean isActive = Boolean.TRUE
}

