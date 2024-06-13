package roomescape.domain.reservationtime;

import java.util.List;

public class ReservationTimes {

    private final List<ReservationTime> reservationTimes;

    public ReservationTimes(List<ReservationTime> reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    public List<ReservationTime> getReservationTimes() {
        return List.copyOf(this.reservationTimes);
    }
}
