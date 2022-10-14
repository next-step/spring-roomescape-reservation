package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsReservation(LocalDate date, LocalTime time);

    Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time);

    List<Reservation> findByDate(LocalDate date);

    boolean deleteByDateAndTime(LocalDate date, LocalTime time);

    void clear();
}
