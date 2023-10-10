package roman.dominic.HotelAlura.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roman.dominic.HotelAlura.dto.GuestDTOCreate;
import roman.dominic.HotelAlura.dto.GuestDTOResponseData;
import roman.dominic.HotelAlura.dto.GuestDTOUpdate;
import roman.dominic.HotelAlura.dto.ReservationDTOListado;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.services.IGuestService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    @Autowired
    private IGuestService guestService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GuestDTOResponseData> create(@RequestBody @Valid GuestDTOCreate guestDTOCreate,
                                         UriComponentsBuilder uriComponentsBuilder){
        try {
            GuestEntity guest = guestService.save(new GuestEntity(guestDTOCreate));
            var showData = new GuestDTOResponseData(guest.getId(),guest.getName(), guest.getLastName(),
                    guest.getBirthdate(), guest.getNationality(), guest.getPhone());
            URI url =uriComponentsBuilder.path("/api/guest/{id}").buildAndExpand(guest.getId()).toUri();
            return ResponseEntity.created(url).body(showData);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GuestDTOResponseData> update(@PathVariable Long id, @RequestBody @Valid GuestDTOUpdate guestDTOUpdate){
        if(!id.equals(guestDTOUpdate.getId())){
            return ResponseEntity.badRequest().build();
        }

        GuestEntity updated = guestService.update(id, guestDTOUpdate);
        if(updated != null){
            var showData = new GuestDTOResponseData(updated.getId(),updated.getName(), updated.getLastName(),
                    updated.getBirthdate(), updated.getNationality(), updated.getPhone());
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(guestService.delete(id)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GuestDTOResponseData> getById(@PathVariable Long id){
        if(guestService.findById(id).isPresent()){
            GuestEntity guest = guestService.findById(id).get();
            var showData = new GuestDTOResponseData(guest.getId(),guest.getName(), guest.getLastName(),
                    guest.getBirthdate(), guest.getNationality(), guest.getPhone());
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<GuestDTOResponseData>> listGuests(
            @RequestParam(name = "lastName", required = false) String lastName,
            Pageable pageable){
        Page<GuestDTOResponseData> guests = guestService.findAll(pageable)
                .map(GuestDTOResponseData::new);

        if (lastName != null) {
            List<GuestDTOResponseData> filteredGuest = guests.stream()
                    .filter(guestDTO -> guestDTO.getLastName().equals(lastName)) // Filtrar por lastName
                    .toList();

            if(filteredGuest.size() > 0){
                guests = new PageImpl<>(filteredGuest, pageable, filteredGuest.size());
            }
        }
        return ResponseEntity.ok(guests);
    }
}
