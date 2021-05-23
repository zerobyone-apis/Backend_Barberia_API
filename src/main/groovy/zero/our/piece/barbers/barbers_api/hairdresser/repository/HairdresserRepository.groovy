package zero.our.piece.barbers.barbers_api.hairdresser.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.hairdresser.model.Hairdresser

@Repository
interface HairdresserRepository extends JpaRepository<Hairdresser, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<Hairdresser>> findByName(@Param("name") final String name);
}
