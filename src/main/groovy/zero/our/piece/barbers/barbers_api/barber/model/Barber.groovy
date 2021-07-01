package zero.our.piece.barbers.barbers_api.barber.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.barber.infrastructure.EnterpriseRoll
import zero.our.piece.barbers.barbers_api.client.infrastructure.Interaction

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "barber")
class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barber_sequence")
    @SequenceGenerator(name = "barber_sequence", sequenceName = "barber_sequence", allocationSize = 1)
    Long id

    // user - barber info
    Long user_id
    String name
    String username
    String phone                                //TODO: Encriptar estos datos
    String email                                //TODO: Encriptar estos datos
    String description

    // Enterprise info
    Long enterprise_id
    EnterpriseRoll roll
    // check: El roll tiene que ser el mismo que el Permission de Usuario para poder ver los accesos correctos.

    String open_work_time                       // horario en el que trabaja, o se rige por la empresa
    String duration_cut_time                    // duracion de sus servicios, 30 min, 40 min, 1 hora. etc.

    // Social Media - Instagram.
    String inst_username                         //TODO: Encriptar estos datos
    String inst_image_profile_url                //TODO: Encriptar estos datos
    Boolean accept_integration                   //TODO: Encriptar estos datos

    String facebook_username                     //TODO: Encriptar estos datos
    String fb_image_profile_url                  //TODO: Encriptar estos datos
    Boolean accept_facebook_integration          //TODO: Encriptar estos datos

    // Analytics data - This data is only required when it necessary do the metrics by batch operation or on hand.
    @Transient
    List<Interaction> interactions // list of interactions
    @Transient
    Long amount_work_service
    @Transient
    Long amount_client
    @Transient
    Long amount_comments
    @Transient
    Long amount_likes_on_comments
    @Transient
    Long amount_shares
    @Transient
    Double average_daily_reserves
    @Transient
    Double prestige

    @Transient
    Long amount_update_sign_on
    @Transient
    Long amount_update_sign_off
    @Transient
    Double duration_session

    // todo: Estos calculos se pueden hacer en base a la cantidad de reservas que haya               tenido el cliente.
    // Preferred Client
    @Transient
    Long client_most_cutest_id
    @Transient
    String client_most_cutest_name

    // Best Service Provided
    @Transient
    Long best_service_provided_id      // WorkService que mas ha realizado y mejor calificado
    @Transient
    String best_service_provided_name // WorkService que mas ha realizado y mejor calificado

    // Creation data
    Instant created_on
    Instant updated_on

    Boolean is_active = Boolean.TRUE
    Boolean is_admin = Boolean.FALSE
}

