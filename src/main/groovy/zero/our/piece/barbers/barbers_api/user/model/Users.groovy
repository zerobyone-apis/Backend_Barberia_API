package zero.our.piece.barbers.barbers_api.user.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name="users")
class Users {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long enterpriseId
    Long barberId
    Long hairdresserId
    String username     //todo: sencive data we need to encrypt
    String password     //todo: sencive data we need to encrypt
    String email        //todo: sencive data we need to encrypt
    DateTime createdOn
    DateTime updateOn
    Boolean isAdmin = Boolean.FALSE
    Boolean status = Boolean.TRUE

    // TODO: hacer las entidades y tables en liquibase para comenzar con los features de Entrerprise.

}
