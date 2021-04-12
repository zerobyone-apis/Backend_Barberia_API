package zero.our.piece.barbers.barbers_api.enterprise.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.enterprise.repository.EnterpriseRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException


@Service
class EnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepository


    List<Enterprise> getAll() {
        enterpriseRepository.findAll()
    }

    Enterprise getById(Long id) {
        def enterprise = enterpriseRepository.findById(id)
        if (enterprise.isEmpty()) throw new ResourceNotFoundException("ENTERPRISE_NOT_FOUND")

        enterprise.get()
    }
}
