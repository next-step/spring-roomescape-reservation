package nextstep.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;

public interface ReservationRepository {

    List<Reservation> findReservationsByDate(LocalDate date);

    long save(LocalDate date, LocalTime time, String name);

    void deleteByLocalDateAndLocalTime(LocalDate date, LocalTime time);

    boolean existReservationByDateAndTime(LocalDate date, LocalTime time);
}
