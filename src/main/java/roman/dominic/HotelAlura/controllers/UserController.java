package roman.dominic.HotelAlura.controllers;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import roman.dominic.HotelAlura.dto.UserDTO;
import roman.dominic.HotelAlura.dto.UserDTOLogin;
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
            userEntity.setPassword(userService.encodePassword(userEntity.getPassword()));
            UserEntity userCreated =  userService.register(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        } catch (RegistroFallidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(userService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body("User " + id + " removed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO){
        if(!id.equals(userDTO.getId())){
            return ResponseEntity.badRequest().build();
        }

        UserEntity updated = userService.update(id, userDTO);
        System.out.println("LLEGO LUEGO DE UPDATED");
        if(updated != null){
            userDTO.setId(updated.getId());
            userDTO.setUserName(updated.getUserName());
            userDTO.setCreateAd(updated.getCreateAd());
            System.out.println("ENTRO ACA");
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
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
}
