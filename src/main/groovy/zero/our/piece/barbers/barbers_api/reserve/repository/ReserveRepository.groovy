package zero.our.piece.barbers.barbers_api.reserve.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.reserve.model.Reserves

import java.time.Instant

@Repository
interface ReserveRepository extends JpaRepository<Reserves, Long> {

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.userId = :userId AND rs.createdOn = :date", nativeQuery = true)
    Reserves findByUserIdAndCreatedOn(@Param("userId") Long userId, @Param("date") String date );

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.userId = :userId", nativeQuery = true)
    List<Reserves> findAllByUserId(@Param("userId") Long userId);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.userId = :userId AND rs.isActive = TRUE", nativeQuery = true)
    List<Reserves> findAllActivesByUserId(@Param("userId") Long userId);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.clientId = :clientId AND rs.isActive = :active", nativeQuery = true)
    List<Reserves> findAllByClientIdAndIsActive(@Param("clientId") Long clientId, @Param("active") Boolean active);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.clientId = :userId AND rs.createdOn >= :currentDate", nativeQuery = true)
    Reserves findByClientIdAndCreatedOn(@Param("userId") Long userId, @Param("currentDate") Instant currentDate );
}
