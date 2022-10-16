package nextstep.domain.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;

public interface ReservationRepository {

    Integer save(Reservation reservation);

    List<Reservation> findAllBy(LocalDate date);

    void delete(LocalDate date, LocalTime time);
}
