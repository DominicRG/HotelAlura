package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import roman.dominic.HotelAlura.models.GuestEntity;

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

    public GuestDTOResponseData(GuestEntity guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.lastName = guest.getLastName();
        this.birthdate = guest.getBirthdate();
        this.nationality = guest.getNationality();
        this.phone = guest.getPhone();
    }
}
