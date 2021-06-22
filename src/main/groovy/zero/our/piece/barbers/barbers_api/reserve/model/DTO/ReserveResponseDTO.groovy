package zero.our.piece.barbers.barbers_api.reserve.model.DTO

import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.services.model.DTO.ServicesResponseDTO
import zero.our.piece.barbers.barbers_api.user.model.DTO.ResponseUserLoginDTO

class ReserveResponseDTO {

    // reserve -  info
    Long id

    // user - barber or hairdresser info
    Enterprise enterprise
    ResponseUserLoginDTO barberUser
    ClientResponseDTO client
    ServicesResponseDTO workService

    // Cost reserve - Los mismos que el servicio pero se añaden los del producto
    Double priceService
    Double productCost
    Double externalServicesCost                                 // Cafeteria, bebidas, etc,
    Double totalCost                                            //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
    Boolean isActive
}
