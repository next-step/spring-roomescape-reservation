package roomescape.reservationTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationTimeController {
	private final ReservationTimeService reservationTimeService;

	public ReservationTimeController(ReservationTimeService reservationTimeService) {
		this.reservationTimeService = reservationTimeService;
	}

	@PostMapping("/times")
	public ResponseEntity<ReservationTimeResponse> saveReservationTime(@RequestBody ReservationTimeRequest request) {
		Long id = reservationTimeService.saveReservationTime(request);
		return ResponseEntity.ok(reservationTimeService.findReservationTime(id));
	}

	@GetMapping("/times")
	public ResponseEntity<List<ReservationTimeResponse>> getReservations() {
		return ResponseEntity.ok(reservationTimeService.findReservationTimes());
	}

	@DeleteMapping("/times/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		reservationTimeService.deleteReservation(id);
		return ResponseEntity.ok().build();
	}
}
