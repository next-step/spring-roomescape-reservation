package nextstep.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservationMemoryRepository implements ReservationRepository {
    private static final List<Reservation> reservations = new ArrayList<>();

    public Long create(Reservation reservation) {
        reservations.add(reservation.withId());

        return (long) reservations.size();
    }

    public Optional<Reservation> findByDateAndTime(String date, String time) {
        return reservations.stream()
            .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date)) && Objects.equals(it.getTime(), LocalTime.parse(time)))
            .findFirst();
    }

    public void removeByDateAndTime(String date, String time) {
        findByDateAndTime(date, time).ifPresent(reservations::remove);
    }

    public List<Reservation> findAllByDate(String date) {
        return reservations.stream()
            .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
            .collect(Collectors.toList());
    }
}
