package roman.dominic.HotelAlura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTOUpdate {
    @NotNull
    private Long id;
    @NotBlank
    private String userName;
}
