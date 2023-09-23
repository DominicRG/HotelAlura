package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.dto.GuestDTOUpdate;
import roman.dominic.HotelAlura.models.GuestEntity;

import java.util.Optional;

public interface IGuestService {
    public GuestEntity save(GuestEntity guestEntity);
    GuestEntity update(Long id, GuestDTOUpdate guestDTOUpdate);
    public boolean delete(Long id);
    Optional<GuestEntity> findById(Long id);
}
