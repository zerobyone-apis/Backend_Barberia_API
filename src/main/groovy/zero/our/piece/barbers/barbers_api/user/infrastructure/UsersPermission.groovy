package zero.our.piece.barbers.barbers_api.user.infrastructure

enum UsersRoles {
    ADMIN, //Full access
    CLIENT, // User Consumer Only
    BARBER, // Profiles Barber, and reserves
    HAIRDRESSER, // Profiles Hairdresser, and reserves
    SUPERVISOR, //Semi full access, got access to barbers, hairdresser, edit, remove, etc, reserves, modifications,etc.
}