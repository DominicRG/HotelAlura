package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityDetails = userRepository.findByUserName(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado en el sistema."));

        GrantedAuthority authority = new SimpleGrantedAuthority(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return new User(
                userEntityDetails.getUserName(),
                userEntityDetails.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
