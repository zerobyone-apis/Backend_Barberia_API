package zero.our.piece.barbers.barbers_api.contact.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType
import zero.our.piece.barbers.barbers_api.client.infrastructure.Interaction

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "client_contact")
class ClientContact {

    @Id
    Long id

    // Client info
    Long userId
    String name     // todo: Del cliente logeado.
    String username // todo: Del cliente logeado.
    String phone

    // Email info
    String subjectEmail         //todo: - Asunto de la consulta
    String descriptionEmail     //      - Body del email
    String emailFrom            //      - Este email es el del usuario logeado.
    String emailTo              //      - Este email es el de la empresa,

    // Metrics & Secure data
    Long timesRequestByEmail     // si este email ya envio mas de 3 emails queda bloqueado.
    Long isAdvisedForBadConduct  // si este user fue advertido por 3 veces que no puede insultar o decir grocerias, queda bloqueado.

    // Creation data
    DateTime createdOn
    Boolean isActive = Boolean.TRUE
}

