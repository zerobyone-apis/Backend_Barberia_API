package zero.our.piece.barbers.barbers_api.producto.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@ToString
@EqualsAndHashCode
@Table(name="images")
class Image {

    @NotEmpty(message = "INVALID_ID")
    String id

    @NotEmpty(message = "INVALID_NAME")
    String originalName

    String url

    Date added

    Integer image_order
}
