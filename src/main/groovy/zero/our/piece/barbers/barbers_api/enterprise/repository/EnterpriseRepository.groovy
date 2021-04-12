package zero.our.piece.barbers.barbers_api.enterprise.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise

@Repository
interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<Enterprise>> findByName(@Param("name") final String name);
}
