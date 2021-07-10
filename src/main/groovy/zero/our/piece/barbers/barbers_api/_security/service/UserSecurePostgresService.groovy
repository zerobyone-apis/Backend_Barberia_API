package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api.user.model.User
import zero.our.piece.barbers.barbers_api.user.repository.UserRepository

import java.nio.file.attribute.UserPrincipalNotFoundException

@Service("Postgres")
class UserSecurePostgresService {

    //@Autowired
    //private UserSecureDAO repository

    @Autowired
    UserRepository repository

    UserSecurity findUserSecurityByUsername(String username) {
        //todo: Agregar capa de busqueda a tabla de usuarios comunes por el username.
        User user = repository.findByUsername(username)
        if(!user.roles) throw new UserPrincipalNotFoundException("User not found with this Username")
        // crear un UserSecurity con los datos obtenidos del usuario existente
        def userDetail = new UserSecurity(
                email: user.email,
                username: user.username,
                password: user.password,
                role: ApplicationUserRole.valueOf(user.roles.name()),
                authorities: ApplicationUserRole.valueOf(user.roles.name()).getGrantedAuthorities(),
                enabled: true,
                credentialsNonExpired: true,
                accountNonLocked: true,
                accountNonExpired: true
        )
        userDetail
    }

    UserSecurity findByEmail(String email){
        UserSecurity user =  repository.findByEmail(email)
        user.authorities = user.role.getGrantedAuthorities()
        user
    }
}
