package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDTOUpdate {
    @NotNull
    Long id;
    private String name;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate birthdate;
    private String nationality;
    private String phone;
}
