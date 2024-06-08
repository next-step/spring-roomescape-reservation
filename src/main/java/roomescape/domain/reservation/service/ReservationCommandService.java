package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.ReservationAppender;
import roomescape.domain.reservation.domain.ReservationCanceler;
import roomescape.domain.reservation.domain.ReservationReader;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.dto.ReservationSearchKey;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationAppender appender;
    private final ReservationReader reader;
    private final ReservationCanceler canceler;

    public ReserveResponse reserve(final ReserveRequest request) {
        verifyDuplicatedReservationNotExist(request);

        final ReservationId id = appender.append(request.toReservationAppend());
        final Reservation reservation = reader.getById(id);
        return ReserveResponse.from(reservation);
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
