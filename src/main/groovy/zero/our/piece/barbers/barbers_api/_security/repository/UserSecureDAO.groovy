package zero.our.piece.barbers.barbers_api._security.repository

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api._security.model.UserSecurity

@Repository
interface UserSecureDAO extends JpaRepository<UserSecurity, Long> {
    UserSecurity findUserSecurityByUsername(String username)
    UserSecurity findByEmail(String email)
}
