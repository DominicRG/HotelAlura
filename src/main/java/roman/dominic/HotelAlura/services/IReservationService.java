package roman.dominic.HotelAlura.services;

import roman.dominic.HotelAlura.dto.CompanionDTO;
import roman.dominic.HotelAlura.dto.ReservationDTORegister;
import roman.dominic.HotelAlura.dto.ReservationDTOResponseData;
import roman.dominic.HotelAlura.dto.ReservationDTOUptade;
import roman.dominic.HotelAlura.models.ReservationEntity;

public interface IReservationService {
    ReservationDTOResponseData save(ReservationDTORegister reservationDTORegister, String username);
    ReservationDTOResponseData getById(Long id);
    Boolean delete(Long id);
    ReservationEntity update(Long id, ReservationDTOUptade reservationDTOUptade);
    Boolean removeCompanion(CompanionDTO companionDTO);
}
