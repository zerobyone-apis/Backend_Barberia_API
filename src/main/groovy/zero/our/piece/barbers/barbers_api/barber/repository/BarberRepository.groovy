package zero.our.piece.barbers.barbers_api.barber.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise

@Repository
interface BarberRepository extends JpaRepository<Barber, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<Barber>> findByName(@Param("name") final String name);


    @Query(value = "SELECT b.* FROM barber b WHERE b.user_id = :user_id", nativeQuery = true)
    Barber findByUserId(@Param("user_id") Long user_id);
}
