package nextstep.repository;

import nextstep.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByDate(String date);

    long save(LocalDate date, LocalTime time, String name);
}
