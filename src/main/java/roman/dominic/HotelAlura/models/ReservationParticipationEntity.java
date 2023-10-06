package roman.dominic.HotelAlura.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "reservation_participation")
@Data
@NoArgsConstructor
public class ReservationParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = roman.dominic.HotelAlura.models.ReservationEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private roman.dominic.HotelAlura.models.ReservationEntity reservation;

    @ManyToOne(targetEntity = roman.dominic.HotelAlura.models.GuestEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private roman.dominic.HotelAlura.models.GuestEntity guest;

    @Column(name = "role")
    private String role;

    public ReservationParticipationEntity(ReservationEntity reservation, GuestEntity guest, String role) {
        this.reservation = reservation;
        this.guest = guest;
        this.role = role;
    }
}
