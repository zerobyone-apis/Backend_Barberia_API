package zero.our.piece.barbers.barbers_api.barber.model.DTO

import zero.our.piece.barbers.barbers_api.barber.infrastructure.EnterpriseRoll

class BarberResponseDTO {

    // barber info;
    Long id
    String name
    String open_work_time
    String duration_cuts_time
    Long enterprise_id

    String username
    String email
    String description
    EnterpriseRoll roll
    Double prestige
    Boolean is_admin

    String facebook_username
    String fb_image_profile_url
    String inst_username
    String inst_image_profile_url
}
