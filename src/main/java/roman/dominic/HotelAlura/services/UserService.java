package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roman.dominic.HotelAlura.dto.UserDTOLogin;
import roman.dominic.HotelAlura.dto.UserDTORegister;
import roman.dominic.HotelAlura.dto.UserDTOUpdate;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.repository.UserRepository;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;

import java.time.LocalDate;
import java.util.Optional;

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
    public UserEntity register(UserDTORegister userDTORegister) throws RegistroFallidoException {
        GuestEntity guestRequest = new GuestEntity(userDTORegister.getGuest());

        if(guestRequest != null){
            GuestEntity guestCreated = guestService.save(guestRequest);
            if(guestCreated!=null){
                var userEntity = new UserEntity(userDTORegister);
                userEntity.setCreateAd(LocalDate.now());
                userEntity.setGuest(guestCreated);
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
    public UserEntity update(Long id, UserDTOUpdate userDTOUpdate) {
        UserEntity existingUserEntity = userRepository.findById(id).orElse(null);

        if(existingUserEntity != null){
            existingUserEntity.setUserName(userDTOUpdate.getUserName());
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

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}
