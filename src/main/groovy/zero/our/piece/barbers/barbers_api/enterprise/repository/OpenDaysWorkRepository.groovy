package zero.our.piece.barbers.barbers_api.enterprise.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.enterprise.infrastructure.DaysToWork
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.enterprise.model.ShopTime

import java.awt.PageAttributes

@Repository
interface OpenDaysWorkRepository extends JpaRepository<DaysToWork, Long> {

    /** Used on the creation of barbers, for now.. **/
    @Query("SELECT dtw FROM DaysToWork dtw WHERE dtw.shop_time_id = :shopTimeId")
    List<DaysToWork> findAllDaysByShopTimeId(@Param("shopTimeId") Long shopTimeId);
}
