package zero.our.piece.barbers.barbers_api.user.model.DTO

import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO

class ResponseUserLoginDTO {
    UserResponseDTO user
    ClientResponseDTO client
    BarberResponseDTO barber
}

