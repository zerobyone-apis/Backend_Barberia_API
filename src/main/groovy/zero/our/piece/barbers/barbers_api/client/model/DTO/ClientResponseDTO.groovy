package zero.our.piece.barbers.barbers_api.client.model.DTO


import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType

class ClientResponseDTO {

    // Client info;
    Long id
    String name         //todo encryptar estos valores
    String phone        //todo encryptar estos valores
    String username     //todo encryptar estos valores
    String email        //todo encryptar estos valores
    Long social_number
    ClientType client_type

    String inst_username
    String inst_photo_url     //todo: para profile del user profile magico de inst no de usuerio
    String inst_image_profile_url
    Boolean accept_integration
}

