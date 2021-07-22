package zero.our.piece.barbers.barbers_api._security.model

import zero.our.piece.barbers.barbers_api.user.model.User

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import java.time.LocalDateTime


@Entity
@Table(name="confirmation_token")
class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "confirmation_token", sequenceName = "confirmation_token_sequence", allocationSize = 1 )
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator =  "confirmation_token" )
    Long id
    String token
    LocalDateTime createdOn
    LocalDateTime expiresOn
    LocalDateTime confirmAt

    @ManyToOne()
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    User user

}
