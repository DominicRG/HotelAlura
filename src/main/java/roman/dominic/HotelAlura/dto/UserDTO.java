package roman.dominic.HotelAlura.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private LocalDate createAd;
}
