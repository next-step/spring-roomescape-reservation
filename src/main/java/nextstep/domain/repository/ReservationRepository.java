package nextstep.domain.repository;

import java.util.List;
import java.util.Optional;
import nextstep.domain.Reservation;

public interface ReservationRepository {

    void save(Reservation reservation);

    Optional<Reservation> findBy(String date, String time);

    boolean exist(Long id);

    List<Reservation> findAllBy(String date);

    void delete(String date, String time);

    void deleteAll();
}
