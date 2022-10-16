package nextstep.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ReservationViewRequest;
import nextstep.controller.dto.ReservationViewResponse;
import nextstep.domain.reservation.Reservation;
import nextstep.domain.reservation.ReservationCommander;
import nextstep.domain.reservation.ReservationFinder;
import nextstep.domain.reservation.dto.ReservationFindCondition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservationQueryController {

  ReservationFinder finder;

  @GetMapping
  public ResponseEntity<List<ReservationViewResponse.Reservation>> findReservation(
      @Valid @ModelAttribute ReservationViewRequest.FindCondition condition) {

    List<Reservation> reservations = finder.findAll(condition.toDomainCondition());
    List<ReservationViewResponse.Reservation> responses = reservations.stream()
        .map(ReservationViewResponse.Reservation::of)
        .toList();

    return ResponseEntity.ok().body(responses);
  }
}
