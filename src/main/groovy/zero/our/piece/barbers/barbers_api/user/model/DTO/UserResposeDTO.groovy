package zero.our.piece.barbers.barbers_api.user.model.DTO

import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission

class UserResponseDTO {

    String email
    String username
    Long socialNumber
    UsersPermission permissionRol
    Boolean isAdmin

}
