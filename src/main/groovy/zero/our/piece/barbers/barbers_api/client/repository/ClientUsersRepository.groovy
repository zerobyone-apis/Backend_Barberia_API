package zero.our.piece.barbers.barbers_api.client.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.user.infrastructure.BarberUsers
import zero.our.piece.barbers.barbers_api.user.infrastructure.ClientUsers

@Repository
interface ClientUsersRepository extends JpaRepository<ClientUsers, Long> {

    @Query(name = "SELECT cu FROM client_users cu WHERE cu.client_id = :id", nativeQuery = true)
    List<ClientUsers> findByClientId(@Param("id") final Long barberId);

    @Query(name = "SELECT cu FROM client_users cu WHERE cu.user_id = :id", nativeQuery = true)
    List<ClientUsers> findByUserId(@Param("id") final Long userId);
}
