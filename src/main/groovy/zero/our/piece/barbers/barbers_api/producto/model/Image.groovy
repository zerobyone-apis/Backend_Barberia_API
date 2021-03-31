package zero.our.piece.barbers.barbers_api.producto.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "images")
class Image {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotEmpty(message = "INVALID_ID")
    String id

    @NotEmpty(message = "INVALID_IMAGE_NAME")
    String imgName

    String urlAvatar
    String banner
    DateTime createdOn
    DateTime deletedOn
    Integer image_order

    Gallery galleryImage() {
        new Gallery(name: imgName, url: urlAvatar, numOrder: image_order)
    }
}

class Gallery {
    String name
    String url
    Integer numOrder
}