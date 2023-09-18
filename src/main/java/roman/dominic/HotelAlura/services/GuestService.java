package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.repository.GuestRepository;

@Service
public class GuestService implements IGuestService{

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public GuestEntity save(GuestEntity guestEntity) {
        return guestRepository.save(guestEntity);
    }

    /*@Override
    public boolean delete(Long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }*/
}
