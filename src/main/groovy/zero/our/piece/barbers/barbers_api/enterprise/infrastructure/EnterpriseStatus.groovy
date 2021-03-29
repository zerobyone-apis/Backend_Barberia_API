package zero.our.piece.barbers.barbers_api.enterprise.infrastructure

import groovy.transform.ToString

@ToString
enum EnterpriseStatus {
    PENDING,
    REJECTED,
    APPROVED
}