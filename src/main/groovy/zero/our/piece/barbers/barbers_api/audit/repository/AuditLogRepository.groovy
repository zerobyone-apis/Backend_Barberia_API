package zero.our.piece.barbers.barbers_api.audit.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.audit.model.AuditLog

@Repository
interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    AuditLog findByUsername(String username);
    AuditLog findByEmail(String email);

}
