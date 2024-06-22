package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservation.ReservationsResponse;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.reservation.create.ReservationCreateResponse;
import roomescape.exception.ErrorCodeResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationsResponse>> findReservations() {
        List<ReservationsResponse> list = reservationService.findReservations();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> create(@Valid @RequestBody ReservationCreateRequest dto) {
        ReservationCreateResponse reservation = reservationService.createReservation(dto);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ErrorCodeResponse.class)
    public ResponseEntity<ErrorCodeResponse> handlerCustomErrorException(ErrorCodeResponse e) {
        ErrorCodeResponse errorMsg = new ErrorCodeResponse(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
}
