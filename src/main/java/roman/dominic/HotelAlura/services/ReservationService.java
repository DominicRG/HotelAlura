package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roman.dominic.HotelAlura.dto.CompanionDTO;
import roman.dominic.HotelAlura.dto.GuestDTOResponseData;
import roman.dominic.HotelAlura.dto.ReservationDTORegister;
import roman.dominic.HotelAlura.dto.ReservationDTOResponseData;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.ReservationEntity;
import roman.dominic.HotelAlura.models.ReservationParticipationEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.models.enums.EROLE;
import roman.dominic.HotelAlura.repository.ReservationRepository;
import roman.dominic.HotelAlura.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService{

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuestService guestService;
    @Autowired
    private ReservationParticipationService participationService;

    @Transactional
    @Override
    public ReservationDTOResponseData save(ReservationDTORegister reservationDTORegister, String username) {
        ReservationEntity reservationRegister = new ReservationEntity(reservationDTORegister);
        ReservationEntity reservationSaved = reservationRepository.save(reservationRegister);

        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(username);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntityHolder = userEntityOptional.get();

            ReservationParticipationEntity participationEntity = new ReservationParticipationEntity(
                    reservationSaved,
                    userEntityHolder.getGuest(),
                    EROLE.HOLDER.toString()
            );

            participationService.save(participationEntity);

            if (reservationDTORegister.getCompanions() != null && !reservationDTORegister.getCompanions().isEmpty()) {
                List<GuestEntity> companionEntities = reservationDTORegister.getCompanions()
                        .stream()
                        .map(companion -> new GuestEntity(companion))
                        .toList();

                companionEntities.forEach(companion -> {
                    guestService.save(companion);
                    participationService.save(new ReservationParticipationEntity(
                            reservationSaved,
                            companion,
                            EROLE.COMPANION.toString()
                    ));
                });
            }

            return new ReservationDTOResponseData(
                    reservationSaved.getId(),
                    reservationSaved.getEntryDate(),
                    reservationSaved.getRetireDate(),
                    reservationSaved.getValue(),
                    reservationSaved.getWayToPay(),
                    new GuestDTOResponseData(userEntityHolder.getGuest()),
                    reservationDTORegister.getCompanions()
            );
        }

        return null;
    }

    @Override
    public ReservationDTOResponseData getById(Long id) {
        List<ReservationParticipationEntity> participationEntityList = participationService.findByReservationId(id);

        if(!participationEntityList.isEmpty() && reservationRepository.findById(id).isPresent()){
            ReservationEntity reservation = reservationRepository.findById(id).get();
            System.out.println(reservation);

            GuestEntity guestHolder = participationEntityList.stream()
                    .filter(data -> "HOLDER".equals(data.getRole()))
                    .map(ReservationParticipationEntity::getGuest)
                    .findFirst()
                    .orElse(null);

            List<CompanionDTO> companionDTOList = participationEntityList.stream()
                    .filter(data -> "COMPANION".equals(data.getRole()))
                    .map(data -> new CompanionDTO(data.getGuest().getName(), data.getGuest().getLastName(), data.getGuest().getPhone()))
                    .toList();

            return new ReservationDTOResponseData(reservation.getId(), reservation.getEntryDate(),
                    reservation.getRetireDate(), reservation.getValue(), reservation.getWayToPay(),
                    new GuestDTOResponseData(guestHolder), companionDTOList);
        }
        return null;
    }
}
