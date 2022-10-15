package nextstep.domain.reservation.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAllByDate(LocalDate date);

    void deleteAll();
}
