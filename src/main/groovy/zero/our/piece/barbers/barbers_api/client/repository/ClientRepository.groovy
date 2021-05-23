package zero.our.piece.barbers.barbers_api.client.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.client.model.Client

@Repository
interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c.* FROM users u INNER JOIN clients c on (u.id = c.user_id) WHERE u.id = :user_id", nativeQuery=true)
    Client findByUserId(@Param("user_id") Long user_id);

    @Query(value = "SELECT c.* FROM clients c WHERE u.email = :email", nativeQuery=true)
    Client findByEmail(@Param("email") String email);

    @Query(value = "SELECT c.* FROM users u INNER JOIN clients c on (u.username = c.username) WHERE c.username = :username", nativeQuery=true)
    Client findByUsername(@Param("username") String username);

}
