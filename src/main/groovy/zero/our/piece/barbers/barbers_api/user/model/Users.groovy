package zero.our.piece.barbers.barbers_api.user.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@ToString
@EqualsAndHashCode
@Table(name="users")
class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id


    // todo: hacer las entidades y tables en liquibase para comenzar con los features de Entrerprise.

}
