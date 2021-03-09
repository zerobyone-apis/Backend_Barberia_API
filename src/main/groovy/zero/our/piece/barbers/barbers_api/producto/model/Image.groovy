package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name="images")
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotEmpty(message = "INVALID_ID")
    String id

    @NotEmpty(message = "INVALID_IMAGE_NAME")
    String imgName

    String urlAvatar
    String banner

    Gallery galleryImage
    Instant createdOn
    Instant removedOn
    Integer image_order
}

class Gallery {
    String name
    String url
    Integer numOrder
}