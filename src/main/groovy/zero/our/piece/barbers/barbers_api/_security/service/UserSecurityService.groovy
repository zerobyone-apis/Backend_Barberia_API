package zero.our.piece.barbers.barbers_api._security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

/*
    todo: ESte servicio esta fuera de uso por el momento
          hasta no continuar con el siguiente tipo de authenticacion no se usara,
                -> actualmente estamos en BASIC AUTH. V
                -> Siguiente paso Form AUTH + JWT token, and cookie sessions.
                -> Sessions cacheadas, + Principal intercept y and hold context del usuario logeado.
*/

//@Service
class UserSecurityService implements UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         return null

        //todo: Hacer la logica de busqueda a la db por username, y validar si tiene permisos y roles, y devolverlos al contexto de seguridad
        //      para poder recuperarlo con el Principal
    }
}

