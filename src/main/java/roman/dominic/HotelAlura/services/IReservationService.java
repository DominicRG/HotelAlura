package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.dto.ReservationDTORegister;
import roman.dominic.HotelAlura.dto.ReservationDTOResponseData;
import roman.dominic.HotelAlura.models.ReservationEntity;

import java.util.Optional;

public interface IReservationService {
    ReservationDTOResponseData save(ReservationDTORegister reservationDTORegister, String username);
    ReservationDTOResponseData getById(Long id);
}
