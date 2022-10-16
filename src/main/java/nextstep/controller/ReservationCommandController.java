package nextstep.controller;

import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ReservationViewRequest;
import nextstep.domain.reservation.ReservationCommander;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationCommandController {

  ReservationCommander commander;

  @PostMapping
  public ResponseEntity<Void> createReservation(@Valid @RequestBody ReservationViewRequest.Create createReq){
    commander.createReservation(createReq.toDomainCommand());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteReservation(@Valid @RequestBody ReservationViewRequest.Delete deleteReq){
    commander.deleteReservation(deleteReq.toDomainCommand());
    return ResponseEntity.ok().build();
  }
}
