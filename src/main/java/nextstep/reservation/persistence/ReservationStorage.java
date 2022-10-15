package nextstep.reservation.persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.domain.Reservation;

public interface ReservationStorage {

    Long insert(Reservation reservation);

    List<Reservation> findByDate(LocalDate date);

    void deleteByDateTime(LocalDate date, LocalTime time);
}
