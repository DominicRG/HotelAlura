package roman.dominic.HotelAlura.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import roman.dominic.HotelAlura.dto.*;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.services.IUserService;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping()
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTORegister userDTORegister,
                                           UriComponentsBuilder uriComponentsBuilder){
        try {
            userDTORegister.setPassword(userService.encodePassword(userDTORegister.getPassword()));
            UserEntity userCreated =  userService.register(userDTORegister);

            var showData = new UserDTOResponseData(userCreated.getId(), userCreated.getUserName(),
                    userCreated.getCreateAd(), new GuestDTOResponseData(userCreated.getGuest().getId(),
                    userCreated.getGuest().getName(), userCreated.getGuest().getLastName(),
                    userCreated.getGuest().getBirthdate(), userCreated.getGuest().getNationality(),
                    userCreated.getGuest().getPhone()));

            URI url = uriComponentsBuilder.path("/api/users/{id}").buildAndExpand(userCreated.getId()).toUri();
            return ResponseEntity.created(url).body(showData);
        } catch (RegistroFallidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(userService.delete(id)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTOResponseData> update(@PathVariable Long id,
                                                      @RequestBody @Valid UserDTOUpdate userDTOUpdate){
        if(!id.equals(userDTOUpdate.getId())){
            return ResponseEntity.badRequest().build();
        }

        UserEntity updated = userService.update(id, userDTOUpdate);
        if(updated != null){
            var showData = new UserDTOResponseData(updated.getId(), updated.getUserName(),
                    updated.getCreateAd(), new GuestDTOResponseData(updated.getGuest().getId(),
                    updated.getGuest().getName(), updated.getGuest().getLastName(),
                    updated.getGuest().getBirthdate(), updated.getGuest().getNationality(),
                    updated.getGuest().getPhone()));
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDTOLogin userDTOLogin){
        if(userService.login(userDTOLogin) != null ){
            return ResponseEntity.status(HttpStatus.OK).body(userService.login(userDTOLogin));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTOResponseData> getById(@PathVariable Long id){
        if(userService.findById(id).isPresent()){
            UserEntity user = userService.findById(id).get();
            var showData = new UserDTOResponseData(user.getId(), user.getUserName(),
                    user.getCreateAd(), new GuestDTOResponseData(user.getGuest().getId(),
                    user.getGuest().getName(), user.getGuest().getLastName(),
                    user.getGuest().getBirthdate(), user.getGuest().getNationality(),
                    user.getGuest().getPhone()));
            return ResponseEntity.ok(showData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
