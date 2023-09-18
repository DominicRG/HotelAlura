package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.dto.UserDTO;
import roman.dominic.HotelAlura.dto.UserDTOLogin;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.UserEntity;

public interface IUserService {
    public UserEntity register(UserEntity userEntity) throws RegistroFallidoException;
    public boolean delete(Long id);
    public UserEntity update(Long id, UserDTO userDTO);
    public Object login(UserDTOLogin userDTOLogin);
    public String encodePassword(String password);
}
