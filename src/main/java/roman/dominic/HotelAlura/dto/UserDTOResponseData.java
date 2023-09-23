package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDTOResponseData {
    private Long id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate createAd;
    private GuestDTOResponseData guest;
}
