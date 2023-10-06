package roman.dominic.HotelAlura.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanionDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String phone;
}
