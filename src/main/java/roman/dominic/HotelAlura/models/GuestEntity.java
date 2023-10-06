package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import roman.dominic.HotelAlura.dto.CompanionDTO;
import roman.dominic.HotelAlura.dto.GuestDTOCreate;
import roman.dominic.HotelAlura.dto.GuestDTOUpdate;

import java.time.LocalDate;

@Entity(name = "guest")
@Data
@NoArgsConstructor
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

    public GuestEntity(GuestDTOCreate guestDTOCreate) {
        this.name = guestDTOCreate.getName();
        this.lastName = guestDTOCreate.getLastName();
        this.birthdate = guestDTOCreate.getBirthdate();
        this.nationality = guestDTOCreate.getNationality();
        this.phone = guestDTOCreate.getPhone();
    }

    public GuestEntity(CompanionDTO companion) {
        this.name = companion.getName();
        this.lastName = companion.getLastname();
        this.phone = companion.getPhone();
    }

    @Override
    public String toString() {
        return "GuestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                ", nationality='" + nationality + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public void updateEntity(GuestDTOUpdate guestDTOUpdate) {
        if(guestDTOUpdate.getName()!=null) this.name = guestDTOUpdate.getName();
        if(guestDTOUpdate.getLastName()!=null) this.lastName = guestDTOUpdate.getLastName();
        if(guestDTOUpdate.getBirthdate()!=null) this.birthdate = guestDTOUpdate.getBirthdate();
        if(guestDTOUpdate.getNationality()!=null) this.nationality = guestDTOUpdate.getNationality();
        if(guestDTOUpdate.getPhone()!=null) this.phone = guestDTOUpdate.getPhone();
    }
}
