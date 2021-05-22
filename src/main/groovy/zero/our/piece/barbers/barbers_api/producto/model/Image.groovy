package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "images")
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_sequence")
    @SequenceGenerator(name = "images_sequence", sequenceName = "images_sequence", allocationSize = 1)
    @NotEmpty(message = "INVALID_ID")
    String id

    @NotEmpty(message = "INVALID_IMAGE_NAME")
    String img_name

    String url_avatar
    String banner
    Instant created_on
    Instant deleted_on
    Long image_order

    Gallery galleryImage() {
        new Gallery(name: img_name, url: url_avatar, numOrder: image_order)
    }
}

class Gallery {
    String name
    String url
    Long numOrder
}