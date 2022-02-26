package zero.our.piece.barbers.barbers_api.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.producto.model.Product

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<Product>> findByName(@Param("name") final String name);
}
