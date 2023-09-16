package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    /*@Column(name = "role")
    private String role;*/

    @Column(name = "create_ad")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate createAd;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "guest_id", unique = true)
    private GuestEntity guest;
}
