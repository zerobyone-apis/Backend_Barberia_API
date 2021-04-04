package zero.our.piece.barbers.barbers_api.contact.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import zero.our.piece.barbers.barbers_api.contact.model.ClientContact
import zero.our.piece.barbers.barbers_api.enterprise.model.Enterprise

@Repository
interface ClientContactRepository extends JpaRepository<ClientContact, Long> {

    //@Query("SELECT bs FROM BarberShop bs WHERE bs.name = :name")
    Optional<List<ClientContact>> findByName(@Param("name") final String name);
}
