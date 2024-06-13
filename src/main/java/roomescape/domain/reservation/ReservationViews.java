package roomescape.domain.reservation;

import java.util.List;

public class ReservationViews {

    private final List<ReservationView> reservationViews;

    public ReservationViews(List<ReservationView> reservationViews) {
        this.reservationViews = reservationViews;
    }

    public List<ReservationView> getReservationViews() {
        return List.copyOf(this.reservationViews);
    }
}
