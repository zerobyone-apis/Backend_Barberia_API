package zero.our.piece.barbers.barbers_api.client.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.client.infrastructure.ClientType
import zero.our.piece.barbers.barbers_api.client.infrastructure.Interaction
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "clients")
class Client {

    @Id
    Long id

    // user - barber or hairdresser info
    Long userId
    String name
    String username
    String phone
    String email
    String socialNumber

    // Social Media - Instagram.
    String instUsername         //TODO: Encriptar estos datos
    URI instImageProfileUrl     //TODO: Encriptar estos datos
    URI instPhotoUrl            //TODO: Encriptar estos datos
    Boolean acceptIntegration   //TODO: Encriptar estos datos

    // Analytics data - This data is only required when it necessary do the metrics by batch operation or on hand.
    @Transient
    List<Interaction> interactions // list of interactions
    @Transient
    Long amountReserves
    @Transient
    Long amountComments
    @Transient
    Long amountCancelReserves
    @Transient
    Long amountLikes
    @Transient
    Long amountShares
    @Transient
    Long amountUpdateReserves
    @Transient
    Long amountUpdateProfile
    @Transient
    Long amountUpdateSignOn
    @Transient
    Long amountUpdateSignOff
    @Transient
    Double durationSession

    // todo: Estos calculos se pueden hacer en base a la cantidad de reservas que haya tenido el cliente.
    // Preferred Barber
    @Transient
    Long preferredBarberId
    @Transient
    String preferredBarberName
    @Transient
    Double timesSelectedBarber

    // Preferred Hairdresser
    @Transient
    Long preferredHairdresserId
    @Transient
    String preferredHairdresserName
    @Transient
    Double timesSelectedHairdresser

    // Preferred Enterprise - BarberShop or StyleShop (HairdresserShops)
    @Transient
    Double timesSelectedEnterprise

    // Creation data
    DateTime createdOn
    DateTime updateOn

    ClientType clientType // create logic by the metrics to know which type of client is.
    Boolean isActive = Boolean.TRUE
}

