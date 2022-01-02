package zero.our.piece.barbers.barbers_api.audit.model


import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import zero.our.piece.barbers.barbers_api.audit.infrastructure.AuditAction
import zero.our.piece.barbers.barbers_api.user.infrastructure.UsersRoles

import javax.persistence.*
import java.time.Instant

@Entity
@ToString
@EqualsAndHashCode
@Table(name = "audit_log")
@SequenceGenerator(name = "audit_log_sequence", sequenceName = "audit_log_sequence", allocationSize = 1)
class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_log_sequence")
    Long id
    String username                   //todo: sencive data we need to encrypt
    String email                      //todo: sencive data we need to encrypt
    Long socialNumber                 //todo: sencive data we need to encrypt

    @Enumerated(EnumType.STRING)
    UsersRoles roles                   //todo: sencive data we need to encrypt

    Long barberId
    Long hairdresserId
    Long clientId
    AuditAction action
    Instant createdOn
    Instant updatedOn
}

