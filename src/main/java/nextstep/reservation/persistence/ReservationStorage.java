package nextstep.reservation.persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.reservation.domain.Reservation;

public interface ReservationStorage {

    Long insert(Reservation reservation);

    List<Reservation> findBy(Long scheduleId, LocalDate date);

    Optional<Reservation> findBy(Long scheduleId, LocalDate date, LocalTime time);

    void deleteBy(Long scheduleId, LocalDate date, LocalTime time);
}
