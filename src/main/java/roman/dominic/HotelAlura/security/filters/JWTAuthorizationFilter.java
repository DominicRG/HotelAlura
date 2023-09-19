package roman.dominic.HotelAlura.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;
import roman.dominic.HotelAlura.services.UserDetailServiceImpl;

import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{
    private JWTUtil jwtUtil;
    UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    public JWTAuthorizationFilter(JWTUtil jwtUtil, UserDetailServiceImpl userDetailServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
