package roomescape.api.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.CreateReservation;
import roomescape.domain.reservation.Reservation;

@RestController
@RequestMapping("reservations")
public class ReservationController {

  private final List<Reservation> reservations = new ArrayList<>();
  private final AtomicLong generator = new AtomicLong(1);

  @GetMapping
  public List<Reservation> findReservations() {
    return reservations;
  }

  @PostMapping
  public Reservation create(@RequestBody CreateReservation createReservation) {
    Reservation reservation = createReservation.toReservation(generator.incrementAndGet());
    reservations.add(reservation);
    return reservation;
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable long id) {
    Reservation removed = reservations.remove(reservations.indexOf(new Reservation(id)));
  }
}
