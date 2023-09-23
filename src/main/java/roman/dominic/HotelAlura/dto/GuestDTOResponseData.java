package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GuestDTOResponseData {
    private Long id;
    private String name;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate birthdate;
    private String nationality;
    private String phone;

}
