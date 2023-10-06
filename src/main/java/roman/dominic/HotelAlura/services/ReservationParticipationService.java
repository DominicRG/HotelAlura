package roman.dominic.HotelAlura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roman.dominic.HotelAlura.models.ReservationParticipationEntity;
import roman.dominic.HotelAlura.repository.ReservationParticipationRepository;

import java.util.List;

@Service
public class ReservationParticipationService implements IReservationParticipationService{

    @Autowired
    private ReservationParticipationRepository repository;
    @Override
    public ReservationParticipationEntity save(ReservationParticipationEntity reservationParticipationEntity) {
        return repository.save(reservationParticipationEntity);
    }

    @Override
    public List<ReservationParticipationEntity> findByReservationId(Long id) {
        return repository.findByReservationId(id);
    }
}
