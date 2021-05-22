package zero.our.piece.barbers.barbers_api.services.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.services.model.WorkServices

@Repository
interface ServicesRepository extends JpaRepository<WorkServices, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    //Optional<List<WorkServices>> findByName(@Param("name") final String name);
}
