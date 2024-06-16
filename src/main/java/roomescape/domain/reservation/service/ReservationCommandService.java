package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;
import roomescape.global.infrastructure.ClockHolder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ClockHolder clockHolder;

    public ReserveResponse reserve(final ReserveRequest request) {
        verifyDuplicatedReservationNotExist(request);

        final Reservation newReservation = Reservation.defaultOf(
                new ReservationGuestName(request.getName()),
                ReservationDateTime.of(request.getDate(), request.getTime()),
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
        final Optional<Reservation> reservationOpt = reservationRepository.findByNameAndDateTime(
                new ReservationGuestName(request.getName()),
                ReservationDateTime.of(request.getDate(), request.getTime())
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
