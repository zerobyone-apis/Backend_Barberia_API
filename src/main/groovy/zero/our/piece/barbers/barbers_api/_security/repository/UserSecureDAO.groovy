package zero.our.piece.barbers.barbers_api._security.repository

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity

/*
    Todo: Esta tabla la dejo por las dudas en caso de querer guardar algunas cosas mas, por ejemplo los token de los usuarios, refresh tokens, y algunas cosas mas.
  *     Antes era la tabla de securitys de usuarios, peroa hora consumo desde la tabla de usuarios.
  *
*/
@Deprecated
@Repository
interface UserSecureDAO extends JpaRepository<UserSecurity, Long> {
    UserSecurity findUserSecurityByUsername(String username)
    UserSecurity findByEmail(String email)
}
