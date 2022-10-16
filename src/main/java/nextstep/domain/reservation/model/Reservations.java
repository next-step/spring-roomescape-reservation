package nextstep.domain.reservation.model;

import nextstep.domain.reservation.exception.ExistReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// todo: domain service
public class Reservations {
    private final AtomicLong seq = new AtomicLong(1);
    private final List<Reservation> reservations;

    public Reservations() {
        this.reservations = new ArrayList<>();
    }

    public Reservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation create(LocalDate date, LocalTime time, String name) {
        if (existByDateTime(date, time)) {
            throw new ExistReservationException(String.format("해당 일시에 이미 예약이 존재합니다. date: %s, time: %s", date, time));
        }
        Reservation reservation = new Reservation(seq.getAndIncrement(), date, time, name);
        reservations.add(reservation);

        return reservation;
    }

    private Boolean existByDateTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .anyMatch(it -> it.sameDateTime(date, time));
    }

    public void cancelByDateTime(LocalDate date, LocalTime time) {
        reservations.stream()
                .filter(it -> it.sameDateTime(date, time))
                .findFirst()
                .ifPresent(reservations::remove);
    }

    public List<Reservation> findAllByDate(LocalDate date) {
        return reservations.stream()
                .filter(it -> it.sameDate(date))
                .collect(Collectors.toList());
    }
}
