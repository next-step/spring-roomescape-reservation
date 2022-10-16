package nextstep.ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.RoomEscapeService;
import nextstep.application.dto.Reservation;
import nextstep.application.dto.ReservationRes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class RoomEscapeController {

  private final RoomEscapeService service;

  @PostMapping
  public ResponseEntity create(@RequestBody Reservation req) {
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
  public ResponseEntity removeReservation(
      @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate date, String time) {
    service.removeReservation(date, time);
    return ResponseEntity.noContent().build();
  }

}
