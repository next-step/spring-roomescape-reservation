package roomescape.domain.reservation.domain;

import lombok.Builder;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.global.infrastructure.ClockHolder;

import java.util.Objects;

public class ReservationAppend {

    private final ReservationGuestName name;
    private final ReservationTimeStamp timeStamp;

    @Builder
    private ReservationAppend(final ReservationGuestName name, final ReservationTimeStamp timeStamp) {
        if (Objects.isNull(name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(timeStamp)) {
            throw ReservationException.nullField("name");
        }

        this.name = name;
        this.timeStamp = timeStamp;
    }

    public Reservation toReservation(final ClockHolder clockHolder) {
        return Reservation.defaultOf(this.name, this.timeStamp, clockHolder);
    }

}
