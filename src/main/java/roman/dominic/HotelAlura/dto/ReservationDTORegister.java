package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import roman.dominic.HotelAlura.models.enums.WayToPay;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReservationDTORegister {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate entryDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate retireDate;
    @NotNull
    private double value;
    @NotNull
    private WayToPay wayToPay;
    private List<CompanionDTO> companions;
}
