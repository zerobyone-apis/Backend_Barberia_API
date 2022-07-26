package zero.our.piece.barbers.barbers_api.audit.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.audit.infrastructure.AuditAction
import zero.our.piece.barbers.barbers_api.audit.model.AuditLog
import zero.our.piece.barbers.barbers_api.audit.repository.AuditLogRepository
import zero.our.piece.barbers.barbers_api.barber.model.DTO.BarberResponseDTO
import zero.our.piece.barbers.barbers_api.client.model.DTO.ClientResponseDTO
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles
import zero.our.piece.barbers.barbers_api.user.model.DTO.UserResponseDTO
import zero.our.piece.barbers.barbers_api.user.model.RegisterLogins
import zero.our.piece.barbers.barbers_api.user.model.User

import java.time.Instant

@Service
@Slf4j
class AuditService {

    @Autowired
    AuditLogRepository auditLogRepository

    List<AuditLog> findAll() {
        auditLogRepository.findAll()
    }

    AuditLog findById(Long id) {
        def foundAudit = auditLogRepository.findById(id).get()
        if (!foundAudit?.id) throw new ResourceNotFoundException("REGISTER_NOT_FOUND")

        foundAudit
    }

    void saveClientLog(ClientResponseDTO client, AuditAction action) {
        auditLogRepository.save(createClientLog(client, action))
    }

    void saveAdminLog(BarberResponseDTO admin, AuditAction action) {
        auditLogRepository.save(createAdminLog(admin, action))
    }

    void saveUserLog(UserResponseDTO user, AuditAction action) {
        auditLogRepository.save(createUserLog(user, action))
    }

    void saveBarberLog(BarberResponseDTO barber, AuditAction action) {
        auditLogRepository.save(createBarberLog(barber, action))
    }

    static AuditLog createClientLog(client, AuditAction action) {
        return buildAuditLog(client, action, UsersRoles.CLIENT)
    }

    static AuditLog createAdminLog(admin, AuditAction action) {
        return  buildAuditLog(admin, action, UsersRoles.ADMIN)
    }

    static AuditLog createUserLog(user, AuditAction action) {
        return buildAuditLog(user, action, UsersRoles.SUPERVISOR)
    }

    static AuditLog createBarberLog(barber, AuditAction action) {
        return buildAuditLog(barber, action, UsersRoles.BARBER)
    }

    private static AuditLog buildAuditLog(data, AuditAction action, UsersRoles role) {
        new AuditLog(
                username: data.username,
                email: data.email,
                socialNumber: data.social_number,
                clientId: data?.id,
                createdOn: Instant.now(),
                roles: role,
                action: action
        )
    }

}

