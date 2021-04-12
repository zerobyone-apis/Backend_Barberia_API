package zero.our.piece.barbers.barbers_api.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.user.model.User

@Repository
interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query( value = "SELECT u.* FROM users u WHERE u.social_number = :social_number AND u.password = :password", nativeQuery=true)
    User findBySocialNumberAndPassword(@Param("social_number") Long social_number, @Param("password") String password)

    @Query( value = "SELECT u.* FROM users u WHERE u.email = :email AND u.password = :password", nativeQuery=true)
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password)
}
