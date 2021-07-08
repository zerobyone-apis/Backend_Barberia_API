package zero.our.piece.barbers.barbers_api._security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import zero.our.piece.barbers.barbers_api._security.infrastructure.ApplicationUserRole

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.persistence.Transient


/*
    TODO: Crear esta entidad de user_roles_authorities
          aca se guardaran los usario con sus diferentes identidades roles, permisos, etcc
          se retro-alimentara de la tabla de users que ya existe, esta tabla es puramente de seguridad.

          todo:
            - validar campos a ver cuales van, estos seguros, falta ver token y demas.
            - crear la tabla en el yaml,
            - crear una lista de los usuarios actualmente creados y quemados en ApplicationSecurityConfig
 */

@Entity
@EqualsAndHashCode
@Table(name = "user_security")
class UserSecurity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_security_sequence")
    @SequenceGenerator(name = "user_security_sequence", sequenceName = "user_security_sequence", allocationSize = 1)
    Long id

    String password
    String username
    String email

    @Enumerated(EnumType.STRING)
    ApplicationUserRole role

    boolean accountNonExpired
    boolean accountNonLocked
    boolean credentialsNonExpired
    boolean enabled

    @Transient
    Set<GrantedAuthority> authorities

    //todo: Validar si esto esta funcionando correctamente
    Collection<? extends GrantedAuthority> getAuthorities() {
        authorities = new HashSet<>();
        authorities.collect {it -> new SimpleGrantedAuthority(role.name())}.collect()
    }
}
