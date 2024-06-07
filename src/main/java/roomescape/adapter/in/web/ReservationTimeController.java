package roomescape.adapter.in.web;

import static roomescape.adapter.mapper.ReservationTimeMapper.mapToDomain;
import static roomescape.adapter.mapper.ReservationTimeMapper.mapToResponse;

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
import roomescape.adapter.in.web.dto.ReservationTimeCommand;
import roomescape.adapter.in.web.dto.ReservationTimeResponse;
import roomescape.adapter.mapper.ReservationTimeMapper;
import roomescape.application.port.in.ReservationTimeUseCase;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

  private final ReservationTimeUseCase reservationTimeUseCase;

  public ReservationTimeController(ReservationTimeUseCase reservationTimeUseCase) {
    this.reservationTimeUseCase = reservationTimeUseCase;
  }

  @PostMapping
  public ResponseEntity<ReservationTimeResponse> createReservationTime(
    @RequestBody ReservationTimeCommand reservationTimeCommand) {
    return ResponseEntity.ok()
                         .body(mapToResponse(
                           reservationTimeUseCase.registerReservationTime(mapToDomain(reservationTimeCommand))));
  }

  @GetMapping
  public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
    List<ReservationTimeResponse> reservationTimes = reservationTimeUseCase.retrieveReservationTimes()
                                                                           .stream()
                                                                           .map(ReservationTimeMapper::mapToResponse)
                                                                           .toList();

    return new ResponseEntity<>(reservationTimes, HttpStatus.OK);

  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteReservation(@PathVariable("id") Long id) {
    reservationTimeUseCase.deleteReservationTime(id);
  }
}
