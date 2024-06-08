package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;

import java.util.List;

public interface ReservationRepository {

    List<ReservationDto> findReservations();

    Reservation create(ReservationDto dto, ReservationTimeDto timeDto);

//    ReservationDto findReservationById(Long id);

    void deleteReservation(Long id);
}
