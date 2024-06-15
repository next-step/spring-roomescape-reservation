package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.ReservationCanceler;
import roomescape.domain.reservation.domain.ReservationReader;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.dto.ReservationSearchKey;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;
import roomescape.global.infrastructure.ClockHolder;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationReader reader;
    private final ReservationCanceler canceler;
    private final ReservationRepository reservationRepository;
    private final ClockHolder clockHolder;

    public ReserveResponse reserve(final ReserveRequest request) {
        verifyDuplicatedReservationNotExist(request);

        final Reservation newReservation = Reservation.defaultOf(
                new ReservationGuestName(request.getName()),
                ReservationTimeStamp.of(request.getDate(), request.getTime()),
                clockHolder
        );

        final Reservation saved = reservationRepository.save(newReservation);
        return ReserveResponse.from(saved);
    }

    public void cancel(final ReservationId reservationId) {
        canceler.cancel(reservationId);
    }

    private void verifyDuplicatedReservationNotExist(final ReserveRequest request) {
        if (reader.activeReservationExists(ReservationSearchKey.from(request))) {
            final Reservation reservation = reader.getActiveReservationBy(ReservationSearchKey.from(request));
            throw DuplicatedReservationException.fromId(ReservationId.from(reservation));
        }
    }
}
