package roomescape.api.reservation;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.CreateReservation;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationService;

@RestController
@RequestMapping("reservations")
public class ReservationController {

  private final ReservationService service;

  public ReservationController(ReservationService service) {
    this.service = service;
  }
  @GetMapping
  public List<Reservation> findReservations() {
    return service.findAll();
  }

  @PostMapping
  public Reservation create(@RequestBody CreateReservation createReservation) {
    return service.create(createReservation);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable long id) {
    service.delete(id);
  }
}
