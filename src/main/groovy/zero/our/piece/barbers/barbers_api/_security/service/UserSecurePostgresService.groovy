package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api._security.repository.UserSecureDAO

@Service("Postgres")
class UserSecurePostgresService {

    @Autowired
    private UserSecureDAO repository


    UserSecurity findUserSecurityByUsername(String username) {
        UserSecurity user = repository.findUserSecurityByUsername(username)
        user.authorities = user.role.getGrantedAuthorities()
        user
    }

    UserSecurity findByEmail(String email){
        UserSecurity user =  repository.findByEmail(email)
        user.authorities = user.role.getGrantedAuthorities()
        user
    }
}
