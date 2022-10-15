package nextstep.domain.repository;

import java.util.List;
import nextstep.domain.Reservation;

public interface ReservationRepository {

    Integer save(Reservation reservation);

    List<Reservation> findAllBy(String date);

    void delete(String date, String time);
}
