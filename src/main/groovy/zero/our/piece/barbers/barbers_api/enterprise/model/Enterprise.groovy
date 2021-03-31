package zero.our.piece.barbers.barbers_api.enterprise.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "enterprise")
class Enterprise {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @NotEmpty(message = "INVALID_NAME")
    @Length(min = 3, max = 200)
    String name

    @NotEmpty(message = "INVALID_LEGAL_NAME")
    @Length(min = 3, max = 200)
    String legalName

    @NotEmpty(message = "INVALID_EMAIL")
    @Length(min = 6, max = 200)
    String email

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String internalPhone

    @NotNull(message = "INVALID_LEGAL_ID")
    @Length(min = 1, max = 200)
    String legalNumber

    @NotNull(message = "INVALID_BRAND")
    Long brandId

    @NotNull(message = "INVALID_OPEN_TIMES")
    Long shopTimeId

    @NotNull(message = "INVALID_ADDRESS_ID")
    Long addressId

    Long ratingId

//    List<Services> services //TODO: SE RESUELVE HACIENDO UNA TABLA CON EL ID DE LA EMPRESA Y ID DE CADA UNO DE LOS SERVICIOS QUE ESTA EMPRESA PROVEEA - Lista de servicios que la empresa brinda

//    List<Provider> providers //TODO: SE RESUELVE HACIENDO UNA TABLA CON EL ID DE LA EMPRESA Y ID DE CADA UNO DE LOS SERVICIOS QUE ESTA EMPRESA PROVEEA - Lista de servicios que la empresa brinda

//    List<Product> products //TODO: SE RESUELVE HACIENDO UNA TABLA CON EL ID DE LA EMPRESA Y ID DE CADA UNO DE LOS SERVICIOS QUE ESTA EMPRESA PROVEEA - Lista de servicios que la empresa brinda

//    List<Users> users //TODO: SE RESUELVE HACIENDO UNA TABLA CON EL ID DE LA EMPRESA Y ID DE CADA UNO DE LOS SERVICIOS QUE ESTA EMPRESA PROVEEA - Lista de servicios que la empresa brinda

    String enterpriseStatus
    //? me queda duda del tipo de status que espero, imagino que es un control, del estado de CREATED - PENDING - ACCEPTED.

    DateTime createdOn
    DateTime updatedOn

    Boolean hasIntegration // Integration with others apis
    Boolean enabled = Boolean.TRUE

    //TODO: Queda temrinar varios de las entidades y realizar todas las tablas en liquibase.
    // See: https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
}
