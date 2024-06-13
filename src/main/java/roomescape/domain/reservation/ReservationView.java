package roomescape.domain.reservation;

import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.validator.ObjectValidator;

public class ReservationView {

    private final ReservationId reservationId;

    private final ReservationName reservationName;

    private final ReservationDate reservationDate;

    private final ReservationTimeId reservationTimeId;

    private final ReservationTimeStartAt reservationTimeStartAt;

    public ReservationView(
            ReservationId reservationId,
            ReservationName reservationName,
            ReservationDate reservationDate,
            ReservationTimeId reservationTimeId,
            ReservationTimeStartAt reservationTimeStartAt
    ) {
        ObjectValidator.validateNotNull(reservationId, reservationName, reservationDate, reservationTimeId, reservationTimeStartAt);
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.reservationTimeStartAt = reservationTimeStartAt;
    }
    
}
