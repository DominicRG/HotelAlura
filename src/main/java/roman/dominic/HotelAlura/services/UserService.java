package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.repository.UserRepository;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuestService guestService;

    @Override
    public UserEntity register(UserEntity userEntity) {
        GuestEntity guestRequest = userEntity.getGuest();

        if(guestRequest!=null){
            GuestEntity guestCreated = guestService.save(guestRequest);
            if(guestCreated!=null){
                userEntity.getGuest().setId(guestCreated.getId());
                return userRepository.save(userEntity);
            } else {
                throw new RegistroFallidoException("Error al crear el huésped");
            }
        } else{
            throw new RegistroFallidoException("Falta información del huésped.");
        }
    }
}
