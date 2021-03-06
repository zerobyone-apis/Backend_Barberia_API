package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "brand_images")
class BrandImages {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_images_sequence")
    @SequenceGenerator(name = "brand_images_sequence", sequenceName = "brand_images_sequence", allocationSize = 1)
    Long id
    Long brand_id
    Long image_id
}
