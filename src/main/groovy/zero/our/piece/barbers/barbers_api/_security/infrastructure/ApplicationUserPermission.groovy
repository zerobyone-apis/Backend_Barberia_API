package zero.our.piece.barbers.barbers_api._security.infrastructure

enum ApplicationUserPermission {
    BARBER_READ             ("brb_read"),
    BARBER_WRITE            ("brb_write"),
    BARBER_EDIT             ("brb_edit"),
    HAIRDRESSER_READ        ("hair_read"),
    HAIRDRESSER_WRITE       ("hair_write"),
    HAIRDRESSER_EDIT        ("hair_edit"),
    CLIENTE_READ            ("cli_read"),
    CLIENT_WRITE            ("cli_write"),
    CLIENT_EDIT             ("cli_edit"),
    USER_READ               ("usr_read"),
    USER_WRITE              ("usr_write"),
    USER_EDIT               ("usr_edit"),
    CLIENT_RESERVE_WRITE    ("cli_resv_write"),
    CLIENT_RESERVE_EDIT     ("cli_resv_edit"),
    CLIENT_RESERVE_READ     ("cli_resv_read"),
    CLIENT_RESERVE_CANCEL   ("cli_resv_cancel"),
    FULL                    ("full"),

    def permission

    ApplicationUserPermission(String permission) {
        this.permission = permission
    }

    def getPermission() {
        permission
    }


}

