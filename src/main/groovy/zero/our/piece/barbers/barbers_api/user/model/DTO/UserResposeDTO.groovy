package zero.our.piece.barbers.barbers_api.user.model.DTO

import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles

class UserResponseDTO {

    String email
    String username
    Long socialNumber
    UsersRoles roles
    Boolean isAdmin

}
