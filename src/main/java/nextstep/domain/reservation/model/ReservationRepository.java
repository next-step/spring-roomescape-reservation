package nextstep.domain.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Boolean existByDateTime(LocalDate date, LocalTime time);

    List<Reservation> findAllByDate(LocalDate date);

    void deleteByDateTime(LocalDate date, LocalTime time);
}
