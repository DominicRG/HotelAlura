package roman.dominic.HotelAlura.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTORegister {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotNull
    @Valid
    private GuestDTOCreate guest;
}
