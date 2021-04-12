package zero.our.piece.barbers.barbers_api.proveedor.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.producto.model.Product
import zero.our.piece.barbers.barbers_api.proveedor.model.Provider

@Repository
interface ProviderRepository extends JpaRepository<Provider, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<Provider>> findByName(@Param("name") final String name);
}
