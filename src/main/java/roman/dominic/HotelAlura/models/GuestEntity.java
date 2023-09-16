package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity(name = "guest")
@Data
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    @JsonFormat(pattern = "yyyy-MM-dd") // Define el formato deseado
    private LocalDate birthdate;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "guest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
