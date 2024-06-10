package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping
	public List<ReservationResponse> getReservations() {
		return reservationService.findReservations();
	}

	@PostMapping
	public ReservationResponse saveReservations(@RequestBody @Valid ReservationRequest request) {
		Long id = reservationService.saveReservation(request);
		return reservationService.findReservation(id);
	}


	@DeleteMapping("/{id}")
	public void deleteReservation(@PathVariable String id) {
		reservationService.deleteReservation(Long.parseLong(id));
	}
}
