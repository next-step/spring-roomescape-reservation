package roomescape.domain.reservation;

import java.util.ArrayList;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return List.copyOf(this.reservations);
    }

    public Reservations add(Reservation reservation) {
        ArrayList<Reservation> reservations = new ArrayList<>(this.reservations);
        reservations.add(reservation);

        return new Reservations(List.copyOf(reservations));
    }
}
