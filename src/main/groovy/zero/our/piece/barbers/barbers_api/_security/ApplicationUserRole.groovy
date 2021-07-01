package zero.our.piece.barbers.barbers_api._security

import static com.google.common.collect.Sets.newHashSet
import static zero.our.piece.barbers.barbers_api._security.ApplicationUserPermission.*

enum ApplicationUserRole {
        ADMIN               (newHashSet(FULL)),
        SUPERVISOR          (newHashSet(BARBER_READ,BARBER_WRITE,USER_READ,USER_WRITE, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_READ, CLIENT_RESERVE_WRITE)),
        BARBER              (newHashSet(BARBER_READ, BARBER_WRITE, CLIENT_RESERVE_READ, CLIENT_RESERVE_EDIT, CLIENT_RESERVE_WRITE)),
        CLIENT              (newHashSet(CLIENT_WRITE,CLIENTE_READ,CLIENT_RESERVE_WRITE,CLIENT_RESERVE_EDIT,CLIENT_RESERVE_READ))

        private final Set<ApplicationUserPermission> permissions

        ApplicationUserRole(permissions) {
                this.permissions = permissions
        }
}