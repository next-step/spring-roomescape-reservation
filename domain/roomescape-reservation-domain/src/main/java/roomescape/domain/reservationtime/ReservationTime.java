package roomescape.domain.reservationtime;

import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;
import roomescape.domain.validator.ObjectValidator;

import java.time.LocalTime;

public class ReservationTime {

    private final ReservationTimeId id;

    private final ReservationTimeStartAt startAt;

    public ReservationTime(ReservationTimeId id, ReservationTimeStartAt startAt) {
        ObjectValidator.validateNotNull(id, startAt);
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id.id();
    }

    public String getFormattedStartAt(String pattern) {
        return startAt.getFormatted(pattern);
    }

    public LocalTime getStartAt() {
        return this.startAt.startAt();
    }
}
