package roomescape.application;

import roomescape.domain.Time;

public class ReservationTimeServiceOutput {

    private final long id;
    private final String startAt;

    private ReservationTimeServiceOutput(Time time) {
        this.id = time.getId();
        this.startAt = time.getStartAt();
    }

    public static ReservationTimeServiceOutput createReservationTimeServiceOutput(Time reservation) {
        return new ReservationTimeServiceOutput(reservation);
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

}
