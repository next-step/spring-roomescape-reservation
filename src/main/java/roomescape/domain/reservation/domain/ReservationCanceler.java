package roomescape.domain.reservation.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.global.infrastructure.ClockHolder;

@Component
@RequiredArgsConstructor
public class ReservationCanceler {

    private final ClockHolder clockHolder;
    private final ReservationReader reader;
    private final ReservationRepository repository;

    public void cancel(final ReservationId id) {
        final Reservation reservation = reader.getById(id);
        final Reservation canceled = reservation.cancel(clockHolder);
        repository.save(canceled);
    }

}
