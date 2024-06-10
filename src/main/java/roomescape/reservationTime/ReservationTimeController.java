package roomescape.reservationTime;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
	private final ReservationTimeService reservationTimeService;

	public ReservationTimeController(ReservationTimeService reservationTimeService) {
		this.reservationTimeService = reservationTimeService;
	}

	@PostMapping
	public ResponseEntity<ReservationTimeResponse> saveReservationTime(@RequestBody @Valid ReservationTimeRequest request) {
		Long id = reservationTimeService.saveReservationTime(request);
		return ResponseEntity.ok(reservationTimeService.findReservationTime(id));
	}

	@GetMapping
	public ResponseEntity<List<ReservationTimeResponse>> getReservations() {
		return ResponseEntity.ok(reservationTimeService.findReservationTimes());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		reservationTimeService.deleteReservation(id);
		return ResponseEntity.ok().build();
	}
}
