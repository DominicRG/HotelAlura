package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import roman.dominic.HotelAlura.dto.UserDTORegister;

import java.time.LocalDate;

@Entity(name = "users")
@Data
@NoArgsConstructor
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id", unique = true)
    private GuestEntity guest;

    public UserEntity(UserDTORegister userDTORegister) {
        this.userName = userDTORegister.getUserName();
        this.password = userDTORegister.getPassword();
        this.guest = new GuestEntity(userDTORegister.getGuest());
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createAd=" + createAd +
                ", guest=" + guest +
                '}';
    }
}
