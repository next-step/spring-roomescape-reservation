package nextstep.domain.reservation.model;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Long create(Reservation reservation);
    void removeByDateAndTime(String date, String time);
    List<Reservation> findAllByDate(String date);
    Optional<Reservation> findByDateAndTime(String date, String time);
}
