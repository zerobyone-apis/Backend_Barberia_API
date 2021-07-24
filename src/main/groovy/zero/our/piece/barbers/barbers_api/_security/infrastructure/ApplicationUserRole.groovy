package zero.our.piece.barbers.barbers_api._security.infrastructure

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import static com.google.common.collect.Sets.newHashSet
import static ApplicationUserPermission.*

enum ApplicationUserRole {
    ADMIN           (newHashSet(BARBER_READ, BARBER_WRITE, BARBER_EDIT, HAIRDRESSER_READ, HAIRDRESSER_EDIT, HAIRDRESSER_WRITE, USER_READ, USER_WRITE, USER_EDIT, CLIENTE_READ, CLIENT_WRITE, CLIENT_EDIT, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_READ, CLIENT_RESERVE_WRITE,CLIENT_RESERVE_CANCEL , FULL)),
    SUPERVISOR      (newHashSet(BARBER_READ, BARBER_WRITE, BARBER_EDIT, HAIRDRESSER_READ, HAIRDRESSER_EDIT, HAIRDRESSER_WRITE, USER_READ, USER_WRITE, USER_EDIT, CLIENTE_READ, CLIENT_WRITE, CLIENT_EDIT, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_READ, CLIENT_RESERVE_WRITE,CLIENT_RESERVE_CANCEL)),
    BARBER          (newHashSet(HAIRDRESSER_READ, HAIRDRESSER_EDIT, CLIENTE_READ, CLIENT_RESERVE_READ, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_WRITE,CLIENT_RESERVE_CANCEL)),
    HAIRDRESSER     (newHashSet(BARBER_READ, BARBER_EDIT, CLIENTE_READ, CLIENT_RESERVE_READ, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_WRITE,CLIENT_RESERVE_CANCEL)),
    CLIENT          (newHashSet(CLIENTE_READ, CLIENT_RESERVE_WRITE, CLIENT_RESERVE_CANCEL))

    private final Set<ApplicationUserPermission> permissions

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions
    }

    Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = permissions.collect { it -> new SimpleGrantedAuthority(it.permission) }
        authorities.add(new SimpleGrantedAuthority("ROLE_${this.name()}"))
        authorities
    }

}