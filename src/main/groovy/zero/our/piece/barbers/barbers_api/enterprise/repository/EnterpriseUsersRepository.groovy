package zero.our.piece.barbers.barbers_api.enterprise.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.EnterpriseUsers

@Repository
interface EnterpriseUsersRepository extends JpaRepository<EnterpriseUsers, Long> {

    Optional<EnterpriseUsers> findById(Long id)

    @Query(value = "SELECT MAX(eu.social_number) FROM enterprise_users eu WHERE eu.enterprise_id = :enterprise_id", nativeQuery = true)
    Long findMaxSocialNumber(@Param("enterprise_id") Long enterprise_id)
}
