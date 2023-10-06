package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.models.ReservationParticipationEntity;

import java.util.List;

public interface IReservationParticipationService {
    ReservationParticipationEntity save(ReservationParticipationEntity reservationParticipationEntity);
    List<ReservationParticipationEntity> findByReservationId(Long id);
}
