package nextsetp.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InmemoryReservationRepository implements ReservationRepository {
    List<Reservation> reservations = new ArrayList<>();

    @Override
    public Long save(Reservation reservation) {
        reservations.add(reservation);
        return (long) (reservations.size());
    }

    @Override
    public Optional<Reservation> findBy(String date, String time) {
        return reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date))
                        && Objects.equals(it.getTime(), LocalTime.parse(time)))
                .findFirst();
    }

    @Override
    public List<Reservation> findAllBy(String date) {
        return reservations.stream()
                .filter(it -> it.getDate().isEqual(LocalDate.parse(date)))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String date, String time) {
        reservations.stream()
                .filter(it -> Objects.equals(it.getDate(), LocalDate.parse(date))
                        && Objects.equals(it.getTime(), LocalTime.parse(time)))
                .findFirst()
                .ifPresent(reservations::remove);
    }
}
