package zero.our.piece.barbers.barbers_api.reserve.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.proveedor.model.Provider
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves

@Repository
interface ReserveRepository extends JpaRepository<Reserves, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    //Optional<List<Reserves>> findByName(@Param("name") final String name);
}
