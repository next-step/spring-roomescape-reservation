package roomescape.domain.reservation;

import java.time.LocalDate;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void delete(Long id);

    Reservation findById(Long reservationId);

    ReservationViews findAllReservationViews();

    boolean existByTimeId(Long timeId);

    boolean existByDateAndTimeId(LocalDate date, Long timeId);
}
