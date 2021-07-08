package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.repository.UserSecureDAO

/*
    todo:       -> Siguiente paso Form AUTH + JWT token, and cookie sessions.
                -> Sessions cacheadas, + Principal intercept y and hold context del usuario logeado.

    bug: InMemory -> cuando se levanta demora, y no concreta los usuarios por un error
*/

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
         UserDetails user = postgresService.findUserSecurityByUsername(username)
        if (!user.username)  throw new UsernameNotFoundException("USERNAME_NOT_FOUND")

        //todo: Hacer la logica de busqueda a la db por username, y validar si tiene permisos y roles, y devolverlos al contexto de seguridad
        //      para poder recuperarlo con el Principal
        user
    }
}

