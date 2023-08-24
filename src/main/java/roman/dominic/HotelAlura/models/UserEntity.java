package roman.dominic.HotelAlura.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String role;

    private LocalDateTime createAd;

    @ManyToOne(targetEntity = roman.dominic.HotelAlura.models.GuestEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_fk")
    private roman.dominic.HotelAlura.models.GuestEntity guest;
}
