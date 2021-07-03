package zero.our.piece.barbers.barbers_api._security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole


/*
    TODO: Crear esta entidad de user_roles_authorities
          aca se guardaran los usario con sus diferentes identidades roles, permisos, etcc
          se retro-alimentara de la tabla de users que ya existe, esta tabla es puramente de seguridad.

          todo:
            - validar campos a ver cuales van, estos seguros, falta ver token y demas.
            - crear la tabla en el yaml,
            - crear una lista de los usuarios actualmente creados y quemados en ApplicationSecurityConfig
 */

//@Entity
//@Table(name = "user_security")
class UserSecurity implements UserDetails {

    String password
    String username
    Set<GrantedAuthority> authorities
    boolean accountNonExpired
    boolean accountNonLocked
    boolean credentialsNonExpired
    boolean enabled
}
