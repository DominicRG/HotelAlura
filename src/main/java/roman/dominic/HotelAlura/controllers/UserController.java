package roman.dominic.HotelAlura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roman.dominic.HotelAlura.exceptions.RegistroFallidoException;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping()
    public ResponseEntity<Object> register(@RequestBody UserEntity userEntity){
        try {
            UserEntity userCreated =  userService.register(userEntity);
            return ResponseEntity.status(201).body(userCreated);
        } catch (RegistroFallidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
