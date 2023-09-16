package roman.dominic.HotelAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roman.dominic.HotelAlura.models.GuestEntity;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}
