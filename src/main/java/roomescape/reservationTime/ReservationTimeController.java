package roomescape.reservationTime;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
	private final ReservationTimeService reservationTimeService;

	public ReservationTimeController(ReservationTimeService reservationTimeService) {
		this.reservationTimeService = reservationTimeService;
	}

	@PostMapping
	public ReservationTimeResponse saveReservationTime(@RequestBody @Valid ReservationTimeRequest request) {
		return reservationTimeService.saveReservationTime(request);
	}

	@GetMapping
	public List<ReservationTimeResponse> getReservations() {
		return reservationTimeService.findReservationTimes();
	}

	@DeleteMapping("/{id}")
	public void deleteReservation(@PathVariable Long id) {
		reservationTimeService.deleteReservation(id);
	}
}
