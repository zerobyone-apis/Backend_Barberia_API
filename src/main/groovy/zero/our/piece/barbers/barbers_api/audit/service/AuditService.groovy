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
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersPermission
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
        auditLogRepository.save(createClientLog(admin, action))
    }

    void saveUserLog(UserResponseDTO user, AuditAction action) {
        auditLogRepository.save(createClientLog(user, action))
    }

    void saveBarberLog(BarberResponseDTO barber, AuditAction action) {
        auditLogRepository.save(createClientLog(barber, action))
    }

    static AuditLog createClientLog(client, AuditAction action) {
        return new AuditLog(
                username: client.username,
                email: client.email,
                socialNumber: client.social_number,
                clientId: client?.id,
                createdOn:  Instant.now(),
                permission: UsersPermission.CLIENT,
                action: action
        )
    }

    static AuditLog createAdminLog(admin, AuditAction action) {
        return new AuditLog(
                username: admin.username,
                email: admin.email,
                socialNumber: admin.social_number,
                barberId: admin.id,
                createdOn:  Instant.now(),
                permission: UsersPermission.ADMIN,
                action: action
        )
    }

    static AuditLog createUserLog(user, AuditAction action) {
        return new AuditLog(
                username: user.username,
                email: user.email,
                socialNumber: user.social_number,
                barberId: user?.id,
                createdOn:  Instant.now(),
                permission: UsersPermission.SUPERVISOR,
                action: action
        )
    }

    static AuditLog createBarberLog(barber, AuditAction action) {
        return new AuditLog(
                username: barber.username,
                email: barber.email,
                socialNumber: barber.social_number,
                barberId: barber?.id,
                createdOn:  Instant.now(),
                permission: UsersPermission.BARBER,
                action: action
        )
    }

}

