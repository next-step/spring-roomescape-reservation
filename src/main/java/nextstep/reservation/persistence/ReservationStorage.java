package nextstep.reservation.persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.reservation.domain.Reservation;

public interface ReservationStorage {

    Long insert(Reservation reservation);

    List<Reservation> findByDate(LocalDate date);

    Optional<Reservation> findByDateTime(LocalDate date, LocalTime time);

    void deleteByDateTime(LocalDate date, LocalTime time);
}
