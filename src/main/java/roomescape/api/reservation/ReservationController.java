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

  private final ReservationRepository repository;

  public ReservationController(ReservationRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Reservation> findReservations() {
    return repository.findAll();
  }

  @GetMapping("{id}")
  public Reservation getById(@PathVariable Long id) {
    return repository.getById(id);
  }

  @PostMapping
  public Reservation create(@RequestBody CreateReservation createReservation) {
    return repository.save(createReservation);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable long id) {
    repository.delete(id);
  }
}
