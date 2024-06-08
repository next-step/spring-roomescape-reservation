package roomescape.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.domain.ReservationAppender;
import roomescape.domain.reservation.domain.ReservationReader;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationAppender appender;
    private final ReservationReader reader;

    public ReserveResponse reserve(final ReserveRequest request) {
        final ReservationId id = appender.append(request.toReservationAppend());
        final Reservation reservation = reader.getById(id);
        return ReserveResponse.from(reservation);
    }

}
