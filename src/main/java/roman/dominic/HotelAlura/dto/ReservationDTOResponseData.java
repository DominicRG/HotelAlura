package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import roman.dominic.HotelAlura.models.ReservationEntity;
import roman.dominic.HotelAlura.models.enums.WayToPay;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ReservationDTOResponseData {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate retireDate;
    private double totalValue;
    private String wayToPay;
    private GuestDTOResponseData mainGuest;
    private List<CompanionDTO> companions;

    public ReservationDTOResponseData(Long id, LocalDate entryDate, LocalDate retireDate, double totalValue, String wayToPay, GuestDTOResponseData mainGuest) {
        this.id = id;
        this.entryDate = entryDate;
        this.retireDate = retireDate;
        this.totalValue = totalValue;
        this.wayToPay = wayToPay;
        this.mainGuest = mainGuest;
    }
}
