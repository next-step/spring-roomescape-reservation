package roomescape.domain.reservation.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservation.repository.ReservationRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationReader {

    private final ReservationRepository repository;

    public Reservation getById(final ReservationId id) {
        return findById(id).orElseThrow(
                () -> new ReservationNotFoundException("Cannot find Reservation matching id=%d".formatted(id.value()))
        );
    }

    private Optional<Reservation> findById(final ReservationId id) {
        return repository.findById(id.value());
    }

}
