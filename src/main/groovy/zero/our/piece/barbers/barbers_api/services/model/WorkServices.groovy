package zero.our.piece.barbers.barbers_api.services.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime
import zero.our.piece.barbers.barbers_api.services.infrastructure.Promos
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesBarber
import zero.our.piece.barbers.barbers_api.services.infrastructure.ServicesHairdresser
import zero.our.piece.barbers.barbers_api.services.infrastructure.WorkServiceStatus

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.sql.Time

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "work_services")
class WorkServices {

    @Id
    Long id
    String serviceName

    // Type Service
    ServicesBarber barberService
    ServicesHairdresser hairdresserService
    Promos promos

    // Cost service
    Double priceService
    Double totalCost        //todo: Validar o aplicar la promocion en el servicio o costo total dependiendo de la promo.
    Boolean underPromotion  //todo: Se aplica alguna promocion?

    // Time Service
    Time durationOfService //todo: esto puede ser un enum con los horarios prestablecidos de tiempo
    DateTime start //fecha y hora
    DateTime end   //fecha y hora se puede calcular en base a la duration pero para persivir mejor el tiempo

    // user & client info
    Long userId
    String username
    Long clientId
    String clientName

    DateTime createdOn
    DateTime updatedOn

    WorkServiceStatus status
}

