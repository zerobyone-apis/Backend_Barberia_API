package zero.our.piece.barbers.barbers_api.client.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.client.model.Client
import zero.our.piece.barbers.barbers_api.contact.model.ClientContact

@Repository
interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c.* FROM users u INNER JOIN clients c on (u.id = c.user_id) WHERE u.id = :user_id", nativeQuery=true)
    Client findByUserId(@Param("user_id") Long user_id);
}
