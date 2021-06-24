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
    Reserves findByUserIdAndData(@Param("userId") final Long userId, @Param("date") final String date );

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.userId = :userId", nativeQuery = true)
    List<Reserves> findAllByUserId(@Param("userId") final Long userId);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.userId = :userId AND rs.isActive = TRUE", nativeQuery = true)
    List<Reserves> findAllActivesByUserId(@Param("userId") final Long userId);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.clientId = :clientId AND rs.isActive = TRUE", nativeQuery = true)
    List<Reserves> findAllByClientId(@Param("clientId") final Long clientId);

    //todo: check this query on db.
    @Query(name = "SELECT rs.* FROM reserves rs WHERE rs.clientId = :userId AND rs.createdOn >= :currentDate", nativeQuery = true)
    Reserves findByClientId(@Param("userId") final Long userId, @Param("currentDate") final Instant currentDate );
}
