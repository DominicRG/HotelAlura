package roman.dominic.HotelAlura.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import roman.dominic.HotelAlura.dto.CompanionDTO;
import roman.dominic.HotelAlura.dto.GuestDTOUpdate;
import roman.dominic.HotelAlura.dto.ReservationDTORegister;
import roman.dominic.HotelAlura.dto.ReservationDTOUptade;
import roman.dominic.HotelAlura.repository.ReservationRepository;
import roman.dominic.HotelAlura.services.GuestService;
import roman.dominic.HotelAlura.services.ReservationParticipationService;
import roman.dominic.HotelAlura.services.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationParticipationEntity> participations = new ArrayList<>();

    public ReservationEntity(ReservationDTORegister reservationDTORegister) {
        this.entryDate = reservationDTORegister.getEntryDate();
        this.retireDate = reservationDTORegister.getRetireDate();
        this.value = reservationDTORegister.getValue();
        this.wayToPay = reservationDTORegister.getWayToPay().toString();
    }

    public void updateEntity(ReservationDTOUptade reservationDTOUptade, GuestService guestService,
                             ReservationRepository reservationRepository,
                             ReservationParticipationService participationService) {
        if(reservationDTOUptade.getEntryDate()!=null) this.entryDate = reservationDTOUptade.getEntryDate();
        if(reservationDTOUptade.getRetireDate()!=null) this.retireDate = reservationDTOUptade.getRetireDate();
        if(reservationDTOUptade.getValue()!=null) this.value = reservationDTOUptade.getValue();
        if(reservationDTOUptade.getWayToPay()!=null) this.wayToPay = reservationDTOUptade.getWayToPay().toString();
        if(reservationDTOUptade.getCompanions()!=null) {
            //var guestMain = this.participations.get(0).getGuest();
            var companions = this.participations.stream().
                    filter(rp -> "COMPANION".equals(rp.getRole())).
                    map(ReservationParticipationEntity::getGuest).
                    toList();
            if(companions.size() == reservationDTOUptade.getCompanions().size()){
                for (int i = 0; i < companions.size(); i++) {
                    GuestEntity companionDB = companions.get(i);
                    CompanionDTO companionDTO = reservationDTOUptade.getCompanions().get(i);

                    companionDB.setName(companionDTO.getName());
                    companionDB.setLastName(companionDTO.getLastname());
                    companionDB.setPhone(companionDTO.getPhone());

                    this.participations.get(i+1).setGuest(companionDB);
                }
            } else {
                // Edita los compañeros existentes
                for (int i = 0; i < companions.size(); i++) {
                    GuestEntity companionDB = companions.get(i);
                    CompanionDTO companionDTO = reservationDTOUptade.getCompanions().get(i);

                    companionDB.setName(companionDTO.getName());
                    companionDB.setLastName(companionDTO.getLastname());
                    companionDB.setPhone(companionDTO.getPhone());

                    this.participations.get(i + 1).setGuest(companionDB);
                }

                // Si hay nuevos compañeros, agrégalos a la lista
                for (int i = companions.size(); i < reservationDTOUptade.getCompanions().size(); i++) {
                    CompanionDTO newCompanionDTO = reservationDTOUptade.getCompanions().get(i);
                    GuestEntity newCompanion = new GuestEntity(newCompanionDTO);

                    // Crea una nueva ReservationParticipationEntity para el nuevo compañero
                    ReservationParticipationEntity newParticipation = new ReservationParticipationEntity();
                    newParticipation.setRole("COMPANION");

                    guestService.save(newCompanion);
                    newParticipation.setGuest(newCompanion);

                    var reservation = reservationRepository.getReferenceById(reservationDTOUptade.getId());
                    newParticipation.setReservation(reservation);

                    participationService.save(newParticipation);

                    // Agrega el nuevo compañero a la lista de participaciones
                    this.participations.add(newParticipation);
                }
            }
        }
    }
}
