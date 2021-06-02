package zero.our.piece.barbers.barbers_api.enterprise.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.enterprise.model.ShopTime
import zero.our.piece.barbers.barbers_api.enterprise.repository.EnterpriseRepository
import zero.our.piece.barbers.barbers_api.enterprise.repository.ShopTimeRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException

@Service
@Slf4j
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
