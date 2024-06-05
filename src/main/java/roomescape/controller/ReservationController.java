package roomescape.controller;

import java.util.List;

import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.service.ReservationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping
	public ResponseEntity<List<ReservationResponse>> getReservations() {
		return ResponseEntity.ok().body(this.reservationService.getReservations());
	}

	@PostMapping
	public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest request) {
		return ResponseEntity.ok().body(this.reservationService.create(request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancel(@PathVariable("id") long id) {
		this.reservationService.cancel(id);
		return ResponseEntity.ok().build();
	}

}
