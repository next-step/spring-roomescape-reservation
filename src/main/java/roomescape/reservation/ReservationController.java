package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping
	public ResponseEntity<List<ReservationResponse>> getReservations() {
		return ResponseEntity.ok().body(reservationService.findReservations());
	}

	@PostMapping
	public ResponseEntity<ReservationResponse> saveReservations(@RequestBody ReservationRequest request) {
		Long id = reservationService.saveReservation(request);
		return ResponseEntity.ok().body(reservationService.findReservation(id));
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
		reservationService.deleteReservation(Long.parseLong(id));
		return ResponseEntity.ok().build();
	}
}
