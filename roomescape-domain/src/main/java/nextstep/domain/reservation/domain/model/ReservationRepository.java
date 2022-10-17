package nextstep.domain.reservation.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    List<Reservation> findAllByDate(LocalDate date);

    void deleteAll();

    void deleteByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time);

    Optional<Reservation> findByScheduleIdAndDateTime(Long scheduleId, LocalDateTime dateTime);
}
