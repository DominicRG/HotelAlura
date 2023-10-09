package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roman.dominic.HotelAlura.dto.*;
import roman.dominic.HotelAlura.models.GuestEntity;
import roman.dominic.HotelAlura.models.ReservationEntity;
import roman.dominic.HotelAlura.models.ReservationParticipationEntity;
import roman.dominic.HotelAlura.models.UserEntity;
import roman.dominic.HotelAlura.models.enums.EROLE;
import roman.dominic.HotelAlura.repository.ReservationRepository;
import roman.dominic.HotelAlura.repository.UserRepository;

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
            System.out.println("ANTES");
            ReservationEntity reservation = reservationRepository.findById(id).get();
            System.out.println("DESPUES");

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

    @Override
    public Boolean delete(Long id) {
        if(reservationRepository.existsById(id)){
            //var listaDeAcompanantes = guestService.findCompanionGuestsByReservationId(id);
            //System.out.println("ESTOS SON LOS DATOS CON EL QUERY:");
            //listaDeAcompanantes.forEach(guestEntity -> System.out.println(guestEntity));


            ReservationEntity reservation = reservationRepository.findById(id).get();
            var listaDeAcompanantes = reservation.getParticipations().stream().
                    filter(data -> "COMPANION".equals(data.getRole()))
                    .map(ReservationParticipationEntity::getGuest)
                            .toList();


            System.out.println("ESTO ES LOS DATOS SOLO USANDO LA ENTIDAD:");
            listaDeAcompanantes.forEach(guestEntity -> System.out.println(guestEntity));

            reservationRepository.deleteById(id);
            listaDeAcompanantes.forEach(companion -> guestService.delete(companion.getId()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ReservationEntity update(Long id, ReservationDTOUptade reservationDTOUptade) {
        ReservationEntity existingReservation = reservationRepository.findById(id).orElse(null);

        if(existingReservation != null){
            existingReservation.updateEntity(reservationDTOUptade, guestService, reservationRepository, participationService);
            return reservationRepository.save(existingReservation);
        }

        return null;
    }
}
