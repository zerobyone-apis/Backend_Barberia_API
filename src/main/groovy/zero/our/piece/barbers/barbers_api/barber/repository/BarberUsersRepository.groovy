package zero.our.piece.barbers.barbers_api.barber.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.barber.model.Barber
import zero.our.piece.barbers.barbers_api.user.infrastructure.BarberUsers

@Repository
interface BarberUsersRepository extends JpaRepository<BarberUsers, Long> {

    @Query(name = "SELECT bs FROM barber_users bs WHERE bs.barber_id = :id", nativeQuery = true)
    List<BarberUsers> findByBarberId(@Param("id") final Long barberId);

    @Query(name = "SELECT bs FROM barber_users bs WHERE bs.user_id = :id", nativeQuery = true)
    List<BarberUsers> findByUserId(@Param("id") final Long userId);
}
