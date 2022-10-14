package nextstep;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reservations {

    private List<Reservation> reservations = new ArrayList<>();

    private Reservations() {
    }

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void make(Reservation reservation) {
        reservations.add(reservation);
    }

    public void cancel(String date, String time) {
        reservations.stream()
            .filter(reservation -> reservation.isSame(date, time))
            .findFirst()
            .ifPresent(reservations::remove);
    }

    public List<Reservation> check(String date) {
        return reservations.stream()
            .filter(reservation -> reservation.isSame(date))
            .collect(Collectors.toList());
    }
}
