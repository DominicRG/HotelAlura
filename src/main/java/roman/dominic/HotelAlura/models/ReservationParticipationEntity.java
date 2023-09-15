package roman.dominic.HotelAlura.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "reservation_participation")
@Data
public class ReservationParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = roman.dominic.HotelAlura.models.ReservationEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private roman.dominic.HotelAlura.models.ReservationEntity reservation;

    @ManyToOne(targetEntity = roman.dominic.HotelAlura.models.GuestEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private roman.dominic.HotelAlura.models.GuestEntity guest;

    private String role;
}
