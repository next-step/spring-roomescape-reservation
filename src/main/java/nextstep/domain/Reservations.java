package nextstep.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reservations {
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        if (isAlreadyBooked(reservation.getReservationTime())) {
            throw new IllegalArgumentException("이미 예약되어 있는 시간입니다.");
        }
        reservations.add(reservation);
    }

    public void remove(ReservationTime reservationTime) {
        reservations.stream()
            .filter(it -> Objects.equals(it.getReservationTime(), reservationTime))
            .findFirst()
            .ifPresent(reservations::remove);
    }

    public List<Reservation> findBy(LocalDate targetDate) {
        return reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), targetDate))
            .toList();
    }

    private boolean isAlreadyBooked(ReservationTime reservationTime) {
        return reservations.stream()
            .anyMatch(it -> Objects.equals(it.getReservationTime(), reservationTime));
    }
}
