package roman.dominic.HotelAlura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import roman.dominic.HotelAlura.models.ReservationEntity;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservationDTOListado {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate retireDate;
    private double totalValue;
    private String wayToPay;

    public ReservationDTOListado(ReservationEntity reservationEntity) {
        this.id = reservationEntity.getId();
        this.entryDate = reservationEntity.getEntryDate();
        this.retireDate = reservationEntity.getRetireDate();
        this.totalValue = reservationEntity.getValue();
        this.wayToPay = reservationEntity.getWayToPay();
    }
}
