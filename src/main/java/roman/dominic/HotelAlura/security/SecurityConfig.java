package roman.dominic.HotelAlura.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import roman.dominic.HotelAlura.security.filters.JWTAuthenticationFilter;
import roman.dominic.HotelAlura.security.filters.JWTAuthorizationFilter;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;
import roman.dominic.HotelAlura.services.UserDetailServiceImpl;

@Configuration
@EnableMethodSecurity(prePostEnabled = true )
public class SecurityConfig  {

    private final JWTUtil jwtUtil;
    private final UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JWTUtil jwtUtil, UserDetailServiceImpl userDetailServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        JWTAuthenticationFilter jwtFilter = new JWTAuthenticationFilter(jwtUtil);
        jwtFilter.setAuthenticationManager(authenticationManager);
        //jwtFilter.setFilterProcessesUrl("/api/login"); Si queremos cambiar la URL del login

        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers("/swagger-ui/index.html#/").permitAll();
                    auth.anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //No manejaremos sesiones.
                })
                //.httpBasic()
                .addFilter(jwtFilter) //Agregamos un filtro jwt de autenticacion.
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) //Agregamos el filtro de autorizacion que se ejecutara antes del primer filtro(autenticacion)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usar el PasswordEncoder por defecto de Spring Security
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailServiceImpl)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
