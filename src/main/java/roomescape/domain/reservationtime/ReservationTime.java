package roomescape.domain.reservationtime;

import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;

public class ReservationTime {

    private final ReservationTimeId id;

    private final ReservationTimeStartAt startAt;

    public ReservationTime(ReservationTimeId id, ReservationTimeStartAt startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id.getId();
    }

    public String getFormattedStartAt(String pattern) {
        return startAt.getFormatted(pattern);
    }


}
