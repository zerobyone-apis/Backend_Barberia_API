package zero.our.piece.barbers.barbers_api._security

enum ApplicationUserPermission {
    BARBER_READ             ("barber:read"),
    BARBER_WRITE            ("barber:write"),
    CLIENTE_READ            ("client:read"),
    CLIENT_WRITE            ("client:write"),
    USER_READ               ("user:read"),
    USER_WRITE              ("user:write"),
    CLIENT_RESERVE_WRITE    ("client_reserve:write"),
    CLIENT_RESERVE_EDIT     ("client_reserve:edit"),
    CLIENT_RESERVE_READ     ("client_reserve:read"),
    FULL                    ("full"),

    def permission

    ApplicationUserPermission(String permission) {
        this.permission = permission
    }

    getPermission() {
        permission
    }
}