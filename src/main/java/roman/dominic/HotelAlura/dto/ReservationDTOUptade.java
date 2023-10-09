package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import roman.dominic.HotelAlura.models.enums.WayToPay;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReservationDTOUptade {
    @NotNull
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate retireDate;
    private Double value;
    private WayToPay wayToPay;
    private List<CompanionDTO> companions;
}
