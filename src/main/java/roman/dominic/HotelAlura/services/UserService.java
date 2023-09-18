package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roman.dominic.HotelAlura.dto.UserDTO;
import roman.dominic.HotelAlura.dto.UserDTOLogin;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.repository.UserRepository;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;

import java.time.LocalDate;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuestService guestService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtil JWTUtil;

    @Override
    @Transactional
    public UserEntity register(UserEntity userEntity) throws RegistroFallidoException {
        GuestEntity guestRequest = userEntity.getGuest();

        if(guestRequest!=null){
            GuestEntity guestCreated = guestService.save(guestRequest);
            if(guestCreated!=null){
                userEntity.getGuest().setId(guestCreated.getId());
                userEntity.setCreateAd(LocalDate.now());
                return userRepository.save(userEntity);
            } else {
                throw new RegistroFallidoException("Error al crear el huésped");
            }
        } else{
            throw new RegistroFallidoException("Falta información del huésped.");
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public UserEntity update(Long id, UserDTO userDTO) {
        UserEntity existingUserEntity = userRepository.findById(id).orElse(null);

        if(existingUserEntity != null){
            existingUserEntity.setUserName(userDTO.getUserName());
            System.out.println("POR SALIR DE SERVICE");
            return userRepository.save(existingUserEntity);
        }

        return null;
    }

    @Override
    public String login(UserDTOLogin userDTOLogin) {
        UserEntity userEntity = userRepository.findByUserName(userDTOLogin.getUserName()).get();

        if(userEntity != null && passwordEncoder.matches(userDTOLogin.getPassword(), userEntity.getPassword())){
            // Las credenciales son válidas, puedes generar un token JWT aquí
            return JWTUtil.generateAccessToken(userEntity.getUserName());
        } else {
            return null;
        }
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
