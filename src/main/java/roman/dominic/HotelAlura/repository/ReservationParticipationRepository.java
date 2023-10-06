package roman.dominic.HotelAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import roman.dominic.HotelAlura.models.ReservationParticipationEntity;

import java.util.List;

@Repository
public interface ReservationParticipationRepository extends JpaRepository<ReservationParticipationEntity, Long> {
    List<ReservationParticipationEntity> findByReservationId(Long reservationId);
}
