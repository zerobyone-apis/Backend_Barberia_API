package zero.our.piece.barbers.barbers_api.audit.infrastructure

enum AuditAction {
    UPDATE,
    LOGIN,
    LOGOUT,
    CLIENT_CREATE_RESERVE,
    CLIENT_UPDATE_RESERVE,
    USER_CANCEL_RESERVES,
    CLIENT_CANCEL_RESERVES,
    MORE_EVENTS //Definir los tipos de eventos y sus nuevos campos.
}