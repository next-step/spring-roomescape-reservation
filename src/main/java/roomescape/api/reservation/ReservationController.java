package roomescape.api.reservation;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.ReservationService;

@RestController
@RequestMapping("reservations")
public class ReservationController {

  private final ReservationService service;

  public ReservationController(ReservationService service) {
    this.service = service;
  }

  @GetMapping
  public List<ReservationListItemResponse> findReservations() {
    return service.findAll().stream().map(ReservationListItemResponse::from).toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReservationListItemResponse create(
      @RequestBody CreateReservationRequest createReservation) {
    return ReservationListItemResponse.from(service.create(createReservation.toDomain()));
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    service.delete(id);
  }


}
