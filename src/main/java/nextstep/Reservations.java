package nextstep;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reservations {

    private List<Reservation> reservations = new ArrayList<>();

    private Reservations() {
    }

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void make(String date, String time, String name) {
        findBy(date, time)
            .ifPresentOrElse(reservation -> {
                throw new ReservationException(String.format("%s %s은 이미 예약되었습니다.", date, time));
            }, () -> reservations.add(new Reservation(date, time, name)));
    }

    public void cancel(String date, String time) {
        findBy(date, time)
            .ifPresent(reservations::remove);
    }

    private Optional<Reservation> findBy(String date, String time) {
        return reservations.stream()
            .filter(reservation -> reservation.isSame(date, time))
            .findFirst();
    }

    public List<Reservation> check(String date) {
        return reservations.stream()
            .filter(reservation -> reservation.isSame(date))
            .collect(Collectors.toList());
    }
}
