package roomescape.reservation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationRepository {
    List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation newReservation) {
        reservations.add(newReservation);
    }

    public void delete(long id) {
        reservations = reservations.stream().filter(r -> r.getId() != id).toList();
    }

    public List<Reservation> toList() {
        return reservations;
    }
}
