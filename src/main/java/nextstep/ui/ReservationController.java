package nextstep.ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.reservation.ReservationService;
import nextstep.application.reservation.dto.Reservation;
import nextstep.application.reservation.dto.ReservationRes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

  private final ReservationService service;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Reservation req) {
    Long id = service.create(req);
    return ResponseEntity.created(URI.create("/reservations/" + id)).build();
  }

  @GetMapping
  public ResponseEntity<List<ReservationRes>> getReservations(
      @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate date) {
    List<ReservationRes> reservations = service.findReservations(date);
    return ResponseEntity.ok(reservations);
  }

  @DeleteMapping
  public ResponseEntity<Void> removeReservation(
      @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate date, String time) {
    service.removeReservation(date, time);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleException(RuntimeException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

}
