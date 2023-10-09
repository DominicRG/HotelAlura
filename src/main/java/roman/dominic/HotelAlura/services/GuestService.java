package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import roman.dominic.HotelAlura.dto.GuestDTOUpdate;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.repository.GuestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService implements IGuestService{

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public GuestEntity save(GuestEntity guestEntity) {
        return guestRepository.save(guestEntity);
    }

    @Override
    public GuestEntity update(Long id, GuestDTOUpdate guestDTOUpdate) {
        GuestEntity existingGuestEntity = guestRepository.findById(id).orElse(null);

        if(existingGuestEntity != null){
            existingGuestEntity.updateEntity(guestDTOUpdate);
            return guestRepository.save(existingGuestEntity);
        }

        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<GuestEntity> findById(Long id) {
        return guestRepository.findById(id);
    }

    @Override
    public List<GuestEntity> findCompanionGuestsByReservationId(Long id) {
        return guestRepository.findCompanionGuestsByReservationId(id);
    }
}
