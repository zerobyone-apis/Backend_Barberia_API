package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity

import static zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole.*

// TODO: DEMORA MUCHO EN CARGAR, Y TIRA ERROR PORQUE NO RECONOCE UN LIST<UserSecurity>

@Service("InMemory")
class UserSecurityServiceInMemory {

    @Autowired
    private PasswordEncoder passwordEncoder


    UserSecurity findUserSecurityByUsername(String username) {
       def result = findAllUsers().find { it -> it.username == username }
        result
    }

    List<UserSecurity> findAllUsers() {
        new ArrayList<UserSecurity>(
                new UserSecurity(
                        username: "zeroDev",
                        password: passwordEncoder.encode("password"),
                        email: "test.admin@gmail.com",
                        role: ADMIN,
                        authorities: ADMIN.getGrantedAuthorities(),
                        accountNonExpired: false,
                        accountNonLocked: true,
                        credentialsNonExpired: true,
                        enabled: true
                        // token: ""
                ),
                new UserSecurity(
                        username: "TeamSupervisor",
                        password: passwordEncoder.encode("password"),
                        email: "test.supervisor@gmail.com",
                        role: SUPERVISOR,
                        authorities: SUPERVISOR.getGrantedAuthorities(),
                        accountNonExpired: false,
                        accountNonLocked: true,
                        credentialsNonExpired: true,
                        enabled: true
                        // token: ""
                ),

                new UserSecurity(
                        username: "TeamBarber",
                        password: passwordEncoder.encode("password"),
                        email: "test.barber@gmail.com",
                        role: BARBER,
                        authorities: BARBER.getGrantedAuthorities(),
                        accountNonExpired: false,
                        accountNonLocked: true,
                        credentialsNonExpired: true,
                        enabled: true
                        // token: ""
                ),

                new UserSecurity(
                        username: "TeamClient",
                        password: passwordEncoder.encode("password"),
                        email: "test.client@gmail.com",
                        role: CLIENT,
                        authorities: CLIENT.getGrantedAuthorities(),
                        accountNonExpired: false,
                        accountNonLocked: true,
                        credentialsNonExpired: true,
                        enabled: true
                        // token: ""
                )

        )
    }


}
