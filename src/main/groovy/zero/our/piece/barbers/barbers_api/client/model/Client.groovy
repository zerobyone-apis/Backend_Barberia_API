package zero.our.piece.barbers.barbers_api.client.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType
import zero.our.piece.barbers.barbers_api.client.infrastructure.Interaction

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "clients")
class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_sequence")
    @SequenceGenerator(name = "client_sequence", sequenceName = "client_sequence", allocationSize = 1)
    Long id

    // user - client info
    Long user_id
    String name
    String username
    String phone
    String email
    Long social_number

    // Social Media - Instagram.
    String inst_username              //TODO: Encriptar estos datos
    String inst_image_profile_url     //TODO: Encriptar estos datos
    String inst_photo_url             //TODO: Encriptar estos datos
    Boolean accept_integration        //TODO: Encriptar estos datos
    ClientType client_type            //      create logic by the metrics to know which type of client is.


    // Analytics data - This data is only required when it necessary do the metrics by batch operation or on hand.
    @Transient
    List<Interaction> interactions // list of interactions
    @Transient
    Long amount_reserves
    @Transient
    Long amount_comments
    @Transient
    Long amount_cancel_reserves
    @Transient
    Long amount_likes
    @Transient
    Long amount_shares
    @Transient
    Long amount_update_reserves
    @Transient
    Long amount_update_profile
    @Transient
    Long amount_update_sign_on
    @Transient
    Long amount_update_sign_off
    @Transient
    Double duration_session

    // todo: Estos calculos se pueden hacer en base a la cantidad de reservas que haya tenido el cliente.
    // Preferred Barber
    @Transient
    Long preferred_barber_id
    @Transient
    String preferred_barber_name
    @Transient
    Double times_selected_barber

    // Preferred Hairdresser
    @Transient
    Long preferred_hairdresser_id
    @Transient
    String preferred_hairdresser_name
    @Transient
    Double times_selected_hairdresser

    // Preferred Enterprise - BarberShop or StyleShop (HairdresserShops)
    @Transient
    Double times_selected_enterprise

    // Bad conduct times
    Long bad_conduct_notifications

    // Creation data
    Instant created_on
    Instant updated_on

    Boolean is_active = Boolean.TRUE
}

