package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.repository.UserRepository


import java.nio.file.attribute.UserPrincipalNotFoundException

@Service ("Postgres")
class UserSecurePostgresService {

    /*
    -> Deprecado, seria para obtener informacion de alguna tabla de UserSecurity donde guarde  no se tokens o algo.. y luego validarlos aca.
        @Deprecated
        @Autowired
        private UserSecureDAO repository

   -> Service para tener la info total del usuario.
        @Autowired
        UserService userService
    */

    @Autowired
    UserRepository repository

    UserSecurity findUserSecurityByUsername(String username) {
        /**
         * todo: En caso de necesitar añadir mas info del usuario al principal, para hacer alguna operacion de seguridad,
         *      o añadirlo directamente al token, como el: Phone, informacion del cliente o informacion del barber
         *      -> ResponseUserLoginDTO dtoUser = userService.findUserToFillReserve(user.id)
         */
        User user = repository.findByUsername(username)
        if (!user.roles) throw new UserPrincipalNotFoundException("User not found with this Username")
        UserSecurity userSecurity = new UserSecurity(
                id: user.id,
                username: user.username,
                password: user.password,
                credentialsNonExpired: true,
                enabled: true,
                accountNonExpired: true,
                accountNonLocked: true,
                authorities: ApplicationUserRole.valueOf(user.roles.name()).getGrantedAuthorities(),
                email: user.email,
                role: ApplicationUserRole.valueOf(user.roles.name())
        )
        userSecurity
    }

    UserSecurity findByEmail(String email) {
        User user = repository.findByEmail(email)
        if (!user.roles) throw new UserPrincipalNotFoundException("User not found with this Email")
        UserSecurity userSecurity = new UserSecurity(
                username: user.username,
                password: user.password,
                credentialsNonExpired: true,
                enabled: true,
                accountNonExpired: true,
                accountNonLocked: true,
                authorities: ApplicationUserRole.valueOf(user.roles.name()).getGrantedAuthorities()
        )
        userSecurity.id = user.id
        userSecurity.email = user.email
        userSecurity.role = ApplicationUserRole.valueOf(user.roles.name())
        userSecurity
    }
}
