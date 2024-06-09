package roomescape.domain.reservation.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.global.infrastructure.ClockHolder;

@Component
@RequiredArgsConstructor
public class ReservationAppender {

    private final ReservationRepository repository;
    private final ClockHolder clockHolder;

    public ReservationId append(final ReservationAppend append) {
        final Reservation reservation = append.toReservation(clockHolder);
        final Long reservationId = repository.save(reservation);
        return new ReservationId(reservationId);
    }

}
