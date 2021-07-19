package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity

@Service
class UserSecurityService implements UserDetailsService {

    @Autowired
    @Qualifier("InMemory")
    private UserSecurityServiceInMemory inMemoryService

    @Autowired
    @Qualifier("Postgres")
    private UserSecurePostgresService postgresService

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurity user = postgresService.findUserSecurityByUsername(username)
        if (!user.username)  throw new UsernameNotFoundException("USERNAME_NOT_FOUND")
        user
    }
}

