package zero.our.piece.barbers.barbers_api.client.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.contact.model.ClientContact

@Repository
interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c.* FROM users u INNER JOIN clients c on (u.id = c.userId) WHERE u.id = :userId", nativeQuery=true)
    Client findByUserId(@Param("userId") Long userId);
}
