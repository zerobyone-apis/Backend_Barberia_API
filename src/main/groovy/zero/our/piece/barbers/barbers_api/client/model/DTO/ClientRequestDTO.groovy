package zero.our.piece.barbers.barbers_api.client.model.DTO

class ClientRequestDTO {

    // User info
    String username
    String password
    String phone
    String email
    Long enterprise_id

    // Client info;
    String name

    String inst_username
    String inst_photo_url
    String inst_image_profile_url
    Boolean accept_integration
}

