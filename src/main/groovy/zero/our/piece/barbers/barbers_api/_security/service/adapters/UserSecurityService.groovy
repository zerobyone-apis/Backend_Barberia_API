package zero.our.piece.barbers.barbers_api._security.service.adapters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity
import zero.our.piece.barbers.barbers_api._security.service.UserSecurePostgresService

@Component("UserSecurityServices")
class UserSecurityService implements UserDetailsService {

    /**
        Parte de la arquitectura exagonal es desacoplar los distintos adaptadores de forma que nos permita
        cambiar de conector de base de datos indiferentemente de lo que hagamos en este caso lo hacemos con
        datos precargados de usuarios en memoria vs un servicio de postgres que consulta sus propios datos.
     */
    // @Autowired
    // @Qualifier("InMemory")
    // private UserSecurityServiceInMemory inMemoryService

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

