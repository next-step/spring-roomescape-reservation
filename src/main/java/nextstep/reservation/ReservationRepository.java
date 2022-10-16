package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsReservation(Long scheduleId, LocalDate date, LocalTime time);

    Optional<Reservation> findByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time);

    List<Reservation> findByDate(LocalDate date);

    boolean deleteByScheduleIdAndDateAndTime(Long scheduleId, LocalDate date, LocalTime time);

    List<Reservation> findByScheduleId(Long scheduleId);

    void clear();
}
