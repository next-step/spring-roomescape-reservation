package roomescape.domain.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.api.response.ReserveResponse;
import roomescape.domain.reservation.application.request.ReserveRequest;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservationtime.application.ReservationTimeRepository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.global.infrastructure.ClockHolder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;
    private final ClockHolder clockHolder;

    public ReserveResponse reserve(final ReserveRequest request) {
        verifyDuplicatedReservationNotExist(request);

        final ReservationTime time = timeRepository.getById(new ReservationTimeId(request.getTimeId()));
        final Reservation newReservation = Reservation.defaultOf(
                new ReservationGuestName(request.getName()),
                new ReservationDate(request.getDate()),
                time,
                clockHolder
        );

        final Reservation saved = reservationRepository.save(newReservation);
        return ReserveResponse.from(saved);
    }

    public void cancel(final ReservationId reservationId) {
        final Reservation reservation = getById(reservationId);
        final Reservation cancelled = reservation.cancel(clockHolder);
        reservationRepository.save(cancelled);
    }

    private void verifyDuplicatedReservationNotExist(final ReserveRequest request) {
        final Optional<Reservation> reservationOpt = reservationRepository.findBy(
                new ReservationGuestName(request.getName()),
                new ReservationDate(request.getDate()),
                new ReservationTimeId(request.getTimeId())
        );

        if (reservationOpt.isEmpty()) {
            return;
        }

        final Reservation reservation = reservationOpt.get();
        if (!reservation.canceled()) {
            throw DuplicatedReservationException.fromId(ReservationId.from(reservation));
        }
    }

    public Reservation getById(ReservationId reservationId) {
        return reservationRepository.findById(reservationId.value())
                .orElseThrow(() -> ReservationNotFoundException.from(reservationId));
    }
}
