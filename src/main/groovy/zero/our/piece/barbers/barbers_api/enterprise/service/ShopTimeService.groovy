package zero.our.piece.barbers.barbers_api.enterprise.service

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.enterprise.model.ShopTime
import zero.our.piece.barbers.barbers_api.enterprise.repository.EnterpriseRepository
import zero.our.piece.barbers.barbers_api.enterprise.repository.OpenDaysWorkRepository
import zero.our.piece.barbers.barbers_api.enterprise.repository.ShopTimeRepository
import zero.our.piece.barbers.barbers_api.magicCube.exception.ResourceNotFoundException

@Service
@Slf4j
class ShopTimeService {

    @Autowired
    ShopTimeRepository shopTimeRepository
    @Autowired
    OpenDaysWorkRepository openDaysWorkRepository

    List<ShopTime> getAllForShopTime(Long enterpriseId) {
        shopTimeRepository.findAllByEnterpriseId(enterpriseId).each { it ->
            it.workDays = openDaysWorkRepository.findAllDaysByShopTimeId(it.id)
        }
    }

    ShopTime getById(Long id) {
        def shopTime = shopTimeRepository.findById(id)
        if (shopTime.isEmpty()) throw new ResourceNotFoundException("ENTERPRISE_NOT_FOUND")
        def shop = shopTime.get()
        shop.workDays = openDaysWorkRepository.findAllDaysByShopTimeId(shop.id)
        shop
    }

    ShopTime getShopTimeByShopTimeId(Long enterpriseId) {
        def shopTime = shopTimeRepository.findByEnterpriseId(enterpriseId)?.get()
        if (!shopTime.id) {
            log.error("We cant find some Shop Time with for this Enterprise ID... Please check.")
        }
        shopTime
    }

}
