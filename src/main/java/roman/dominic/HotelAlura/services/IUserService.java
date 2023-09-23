package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.dto.UserDTOLogin;
import roman.dominic.HotelAlura.dto.UserDTORegister;
import roman.dominic.HotelAlura.dto.UserDTOUpdate;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.UserEntity;

import java.util.Optional;

public interface IUserService {
    public UserEntity register(UserDTORegister userDTORegister) throws RegistroFallidoException;
    public boolean delete(Long id);
    public UserEntity update(Long id, UserDTOUpdate userDTOUpdate);
    public String login(UserDTOLogin userDTOLogin);
    public String encodePassword(String password);
    public Optional<UserEntity> findById(Long id);
}
