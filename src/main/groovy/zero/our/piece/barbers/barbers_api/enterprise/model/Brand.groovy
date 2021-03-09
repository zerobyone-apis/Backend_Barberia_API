package zero.our.piece.barbers.barbers_api.enterprise.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.validator.constraints.Length
import zero.our.piece.barbers.barbers_api.producto.model.Image
import zero.our.piece.barbers.barbers_api.services.model.Services

import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name="brand")
class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @NotEmpty(message = "INVALID_BRAND_NAME")
    @Length(min = 3, max = 200)
    String name

    String title
    String slogan

    @NotEmpty(message = "INVALID_PHONE")
    @Length(min = 5, max = 40)
    String preferredPhone
    //TODO: This is the number to show on the image on front or the marketing campaign

    @ElementCollection(targetClass = Image.class)
    @CollectionTable(name = "images" , joinColumns = @JoinColumn(name= "id"))
    List<Image> images

    Instant createdOn
    Instant updatedOn
    Instant deletedOn
    Boolean enabled = Boolean.TRUE

}
