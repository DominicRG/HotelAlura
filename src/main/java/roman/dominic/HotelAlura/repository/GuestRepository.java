package roman.dominic.HotelAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roman.dominic.HotelAlura.models.GuestEntity;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}
