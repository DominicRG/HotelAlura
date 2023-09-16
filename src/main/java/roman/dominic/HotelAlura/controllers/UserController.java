package roman.dominic.HotelAlura.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roman.dominic.HotelAlura.models.UserEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping()
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(userEntity);
    }
}
