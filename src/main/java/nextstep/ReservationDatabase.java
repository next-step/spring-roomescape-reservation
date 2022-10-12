package nextstep;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReservationDatabase {

    public final List<Reservation> reservations = new ArrayList<>();

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public boolean existsReservation(LocalDate date, LocalTime time) {
        return this.findByDateAndTime(date, time).isPresent();
    }

    public Optional<Reservation> findByDateAndTime(LocalDate date, LocalTime time) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), date) && Objects.equals(it.getTime(), time))
                .findAny();
    }

    public List<Reservation> findByDate(LocalDate date) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), date))
                .toList();
    }

    public boolean deleteByDateAndTime(LocalDate date, LocalTime time) {
        Optional<Reservation> findReservation = findByDateAndTime(date, time);
        if (findReservation.isPresent()) {
            reservations.remove(findReservation.get());
            return true;
        }
        return false;
    }
}
