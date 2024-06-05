package roomescape.controller;

import java.util.List;

import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

	private final ReservationTimeService reservationTimeService;

	ReservationTimeController(ReservationTimeService reservationTimeService) {
		this.reservationTimeService = reservationTimeService;
	}

	@PostMapping
	public ResponseEntity<ReservationTimeResponse> create(@RequestBody ReservationTimeRequest request) {
		return ResponseEntity.ok().body(this.reservationTimeService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<ReservationTimeResponse>> getReservationTimes() {
		return ResponseEntity.ok().body(this.reservationTimeService.getReservationTimes());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") long id) {
		this.reservationTimeService.delete(id);
		return ResponseEntity.ok().build();
	}

}
