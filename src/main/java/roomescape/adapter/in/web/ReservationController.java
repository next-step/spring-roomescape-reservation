package roomescape.adapter.in.web;

import static roomescape.adapter.mapper.ReservationMapper.mapToDomain;
import static roomescape.adapter.mapper.ReservationMapper.mapToResponse;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.adapter.in.web.dto.ReservationCommand;
import roomescape.adapter.in.web.dto.ReservationResponse;
import roomescape.adapter.mapper.ReservationMapper;
import roomescape.application.port.in.ReservationUseCase;
import roomescape.validator.DateTimeValidator;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationUseCase reservationUseCase;

  public ReservationController(ReservationUseCase reservationUseCase) {
    this.reservationUseCase = reservationUseCase;
  }

  @GetMapping
  public ResponseEntity<List<ReservationResponse>> getReservations() {
    List<ReservationResponse> reservations = reservationUseCase.retrieveReservations()
                                                               .stream()
                                                               .map(ReservationMapper::mapToResponse)
                                                               .toList();

    return new ResponseEntity<>(reservations, HttpStatus.OK);

  }

  @PostMapping
  public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCommand reservationCommand) {
    DateTimeValidator.previousDateTimeCheck(reservationCommand.date(), reservationCommand.time());
    return ResponseEntity.ok()
                         .body(mapToResponse(reservationUseCase.registerReservation(mapToDomain(reservationCommand))));
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteReservation(@PathVariable("id") Long id) {
    reservationUseCase.deleteReservation(id);
  }
}
