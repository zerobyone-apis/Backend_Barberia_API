package zero.our.piece.barbers.barbers_api.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.user.model.RegisterLogins

@Repository
interface RegisterLoginRepository extends JpaRepository<RegisterLogins, Long> {

    Optional<RegisterLogins> findByUsername(String username);
    Optional<RegisterLogins> findByEmail(String email);

    //TODO: hacer la prueba si lo consigo con NamingQuery, sino usar query.
    //@Query( value = "SELECT u.* FROM Users u WHERE u.social_number = :social_number AND u.password = :password", nativeQuery=true)
    //RegisterLogins findBySocialNumberAndPassword(@Param("social_number") Long socialNumber, @Param("password") String password)

    //TODO: hacer la prueba si lo consigo con NamingQuery, sino usar query.
    //@Query( value = "SELECT u.* FROM Users u WHERE u.email = :email AND u.password = :password", nativeQuery=true)
    //RegisterLogins findByEmailAndPassword(@Param("email") String email, @Param("password") String password)
}
