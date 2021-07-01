package zero.our.piece.barbers.barbers_api.enterprise.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise
import zero.our.piece.barbers.barbers_api.enterprise.model.ShopTime

@Repository
interface ShopTimeRepository extends JpaRepository<ShopTime, Long> {

    /** Used on the creation of barbers, for now.. **/
    //@Query("SELECT st.* FROM shop_time st WHERE st.enterprise_id = :enterpriseId", nativeQuery = true)
    @Query("SELECT st FROM ShopTime st WHERE st.enterprise_id = :enterpriseId")
    Optional<ShopTime> findByEnterpriseId(@Param("enterpriseId") final Long enterpriseId);

    //@Query("SELECT st.* FROM shop_time st WHERE st.enterprise_id = :enterpriseId", nativeQuery = true)
    @Query("SELECT st FROM ShopTime st WHERE st.enterprise_id = :enterpriseId")
    List<ShopTime> findAllByEnterpriseId(@Param("enterpriseId") final Long enterpriseId);
}
