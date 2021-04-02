package zero.our.piece.barbers.barbers_api.barber.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.barber.infrastructure.EnterpriseRoll
import zero.our.piece.barbers.barbers_api.client.infrastructure.Interaction

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "barber")
class Barber {

    @Id
    Long id

    // user - barber info
    Long userId
    String name
    String username
    String phone        //TODO: Encriptar estos datos
    String email        //TODO: Encriptar estos datos
    String description

    // Enterprise info
    Long enterpriseId
    EnterpriseRoll roll // check: El roll tiene que ser el mismo que el Permission de Usuario para poder ver los accesos correctos.

    String openWorkTime     // horario en el que trabaja, o se rige por la empresa
    String durationCutsTime // duracion de sus servicios, 30 min, 40 min, 1 hora. etc.

    // Social Media - Instagram.
    String instUsername         //TODO: Encriptar estos datos
    URI instImageProfileUrl     //TODO: Encriptar estos datos
    Boolean acceptIntegration   //TODO: Encriptar estos datos

    String facebookUsername             //TODO: Encriptar estos datos
    URI fbImageProfileUrl               //TODO: Encriptar estos datos
    Boolean acceptFacebookIntegration   //TODO: Encriptar estos datos

    // Analytics data - This data is only required when it necessary do the metrics by batch operation or on hand.
    @Transient
    List<Interaction> interactions // list of interactions
    @Transient
    Long amountWorkService
    @Transient
    Long amountClient
    @Transient
    Long amountComments
    @Transient
    Long amountLikesOnComments
    @Transient
    Long amountShares
    @Transient
    Double averageDailyReserves
    @Transient
    Double prestige

    @Transient
    Long amountUpdateSignOn
    @Transient
    Long amountUpdateSignOff
    @Transient
    Double durationSession

    // todo: Estos calculos se pueden hacer en base a la cantidad de reservas que haya tenido el cliente.
    // Preferred Client
    @Transient
    Long clientMostCutestId
    @Transient
    String clientMostCutestName

    // Best Service Provided
    @Transient
    Long bestServiceProvidedId      // WorkService que mas ha realizado y mejor calificado
    @Transient
    String bestServiceProvidedName // WorkService que mas ha realizado y mejor calificado

    // Creation data
    DateTime createdOn
    DateTime updateOn

    Boolean isActive = Boolean.TRUE
}

