package zero.our.piece.barbers.barbers_api.contact.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "client_contact")
class ClientContact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_cli_sequence")
    @SequenceGenerator(name = "contact_cli_sequence", sequenceName = "contact_cli_sequence", allocationSize = 1)
    Long id

    // Client info
    Long user_id
    String name     // todo: Del cliente logeado.
    String username // todo: Del cliente logeado.
    String phone

    // Email info
    String subject_email         //todo: - Asunto de la consulta
    String description_email     //      - Body del email
    String email_from            //      - Este email es el del usuario logeado.
    String email_to              //      - Este email es el de la empresa,

    // Metrics & Secure data
    Long times_request_by_email     // si este email ya envio mas de 3 emails queda bloqueado.
    Long is_advised_for_bad_conduct // si este user fue advertido por 3 veces que no puede insultar o decir grocerias, queda bloqueado.

    // Creation data
    Instant created_on
    Boolean is_active = Boolean.TRUE
}

