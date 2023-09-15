package roman.dominic.HotelAlura.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "guest")
@Data
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String last_name;

    private LocalDateTime birthdate;

    private String nationality;

    private String phone;

    @OneToOne(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
