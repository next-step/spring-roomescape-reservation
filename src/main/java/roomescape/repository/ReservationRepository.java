package roomescape.repository;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;

import java.util.List;

public interface ReservationRepository {

    List<ReservationDto> findReservations();

    Long create(ReservationDto dto);

    ReservationDto findReservationByid(Long id);

    void deleteReservation(Long id);
}
