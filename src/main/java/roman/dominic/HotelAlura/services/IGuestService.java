package roman.dominic.HotelAlura.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import roman.dominic.HotelAlura.dto.GuestDTOUpdate;
import roman.dominic.HotelAlura.models.GuestEntity;

import java.util.List;
import java.util.Optional;

public interface IGuestService {
    public GuestEntity save(GuestEntity guestEntity);
    GuestEntity update(Long id, GuestDTOUpdate guestDTOUpdate);
    public boolean delete(Long id);
    Optional<GuestEntity> findById(Long id);
    List<GuestEntity> findCompanionGuestsByReservationId(Long id);
    GuestEntity findByNameAndLastNameAndPhone(String name, String lastName, String phone);
    Page<GuestEntity> findAll(Pageable pageable);
}
