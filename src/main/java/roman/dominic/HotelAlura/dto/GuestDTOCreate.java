package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDTOCreate {

    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate birthdate;
    @NotBlank
    private String nationality;
    @NotBlank
    private String phone;
}
