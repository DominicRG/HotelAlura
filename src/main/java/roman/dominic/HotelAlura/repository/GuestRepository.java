package roman.dominic.HotelAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import roman.dominic.HotelAlura.models.GuestEntity;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    @Query("""
        SELECT g FROM guest g
        WHERE g.id IN 
        (SELECT rp.guest.id FROM reservation_participation rp 
        WHERE rp.reservation.id = :reservationId 
        AND rp.role = 'COMPANION')
        """)
    List<GuestEntity> findCompanionGuestsByReservationId(Long reservationId);
    GuestEntity findByNameAndLastNameAndPhone(String name, String lastName, String phone);
}
