package zero.our.piece.barbers.barbers_api._security.service.adapters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity

import static zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole.*

// TODO: DEMORA MUCHO EN CARGAR, Y TIRA ERROR PORQUE NO RECONOCE UN LIST<UserSecurity>

// @Service("InMemory")
class UserSecurityServiceInMemory {

    @Autowired
    private PasswordEncoder passwordEncoder

    UserSecurity findUserSecurityByUsername(String username) {
        def result = findAllUsers().find { it -> it.username == username }
        result
    }

    List<UserSecurity> findAllUsers() {
        new ArrayList<UserSecurity>(
                this.createUserSecurity("zeroDev", "password", "test.admin@gmail.com", ADMIN, false),
                this.createUserSecurity("TeamSupervisor", "password", "test.supervisor@gmail.com", SUPERVISOR, false),
                this.createUserSecurity("TeamBarber", "password", "test.barber@gmail.com", BARBER, false),
                this.createUserSecurity("TeamHairdresser", "password", "test.hairdresser@gmail.com", HAIRDRESSER, false),
                this.createUserSecurity("TeamClient", "password", "test.client@gmail.com", CLIENT, true)
        )
    }

    def createUserSecurity(username, password, email, role, accountNonExpired) {
        def user = new UserSecurity(
                username: username,
                password: passwordEncoder.encode(password),
                authorities: role.getGrantedAuthorities(),
                accountNonExpired: accountNonExpired,
                accountNonLocked: true,
                credentialsNonExpired: true,
                enabled: true
        )
        user.role = role
        user.email = email
        user.authorities = role.getGrantedAuthorities()
        user
    }
}
