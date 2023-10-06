package roman.dominic.HotelAlura.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roman.dominic.HotelAlura.dto.ReservationDTORegister;
import roman.dominic.HotelAlura.dto.ReservationDTOResponseData;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;
import roman.dominic.HotelAlura.services.IReservationService;

import java.net.URI;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private IReservationService reservationService;
    private JWTUtil jwtUtil;

    @Autowired
    public ReservationController(IReservationService reservationService, JWTUtil jwtUtil) {
        this.reservationService = reservationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationDTOResponseData> create(@RequestBody @Valid
                                                             ReservationDTORegister reservationDTORegister,
                                                             UriComponentsBuilder uriComponentsBuilder,
                                                             HttpServletRequest request){
        try {
            String username = null;
            String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
                String token = tokenHeader.substring(7);
                if (jwtUtil.isTokenValid(token)){
                    username = jwtUtil.getUsernameFromToken(token);
                }
            }

            ReservationDTOResponseData responseData = reservationService.save(reservationDTORegister, username);
            URI url =uriComponentsBuilder.path("/api/reservation/{id}").buildAndExpand(responseData.getId()).toUri();
            return ResponseEntity.created(url).body(responseData);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationDTOResponseData> getById(@PathVariable Long id){
        if(reservationService.getById(id) !=  null){
            ReservationDTOResponseData showData = reservationService.getById(id);
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
