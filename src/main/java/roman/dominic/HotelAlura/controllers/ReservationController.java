package roman.dominic.HotelAlura.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roman.dominic.HotelAlura.dto.*;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.ReservationEntity;
import roman.dominic.HotelAlura.models.ReservationParticipationEntity;
import roman.dominic.HotelAlura.security.jwt.JWTUtil;
import roman.dominic.HotelAlura.services.IReservationService;

import java.net.URI;
import java.util.List;

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

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(reservationService.delete(id)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationDTOResponseData> update(@PathVariable Long id, @RequestBody @Valid ReservationDTOUptade reservationDTOUptade){
        if(!id.equals(reservationDTOUptade.getId())){
            return ResponseEntity.badRequest().build();
        }

        ReservationEntity reservationUpdated = reservationService.update(id, reservationDTOUptade);

        GuestEntity guestHolder = reservationUpdated.getParticipations().stream()
                .filter(data -> "HOLDER".equals(data.getRole()))
                .map(ReservationParticipationEntity::getGuest)
                .findFirst()
                .orElse(null);

        var companionDTOList = reservationUpdated.getParticipations().stream()
                .filter(data -> "COMPANION".equals(data.getRole()))
                .map(data -> new CompanionDTO(data.getGuest().getName(), data.getGuest().getLastName(), data.getGuest().getPhone()))
                .toList();

        var showData = new ReservationDTOResponseData(reservationUpdated.getId(), reservationUpdated.getEntryDate(),
                reservationUpdated.getRetireDate(), reservationUpdated.getValue(), reservationUpdated.getWayToPay(),
                new GuestDTOResponseData(guestHolder), companionDTOList);

        if(reservationUpdated != null){
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/companion")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> removeCompanion(@RequestBody CompanionDTO companionDTO){
        if(reservationService.removeCompanion(companionDTO)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ReservationDTOListado>> listReservations(
            @RequestParam(name = "id", required = false) Long id,
            Pageable pageable){

        Page<ReservationDTOListado> reservations = reservationService.findAll(pageable)
                .map(ReservationDTOListado::new);

        if (id != null) {
            List<ReservationDTOListado> filteredReservations = reservations.stream()
                    .filter(reservationDTO -> reservationDTO.getId() == id) // Filtrar por ID
                    .toList();

            if(filteredReservations.size() > 0){
                reservations = new PageImpl<>(filteredReservations, pageable, filteredReservations.size());
            }
        }

        return ResponseEntity.ok(reservations);
    }
}
