package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "reservation")
@Data
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate entryDate;

    @Column(name = "retire_date")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate retireDate;

    @Column(name = "value")
    private double value;

    @Column(name = "way_to_pay")
    private String wayToPay;
}
