package com.nextstep.web.reservation.controller;

import com.nextstep.web.reservation.app.ReservationService;
import com.nextstep.web.reservation.dto.CreateReservationRequest;
import com.nextstep.web.reservation.dto.ReservationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read(@RequestParam String date) {
        return ResponseEntity.ok(reservationService.findAllBy(date));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateReservationRequest request) {
        Long id = reservationService.save(request);
        return ResponseEntity.created(URI.create("/reservation/" + id)).build();
    }

    @DeleteMapping
    private ResponseEntity<Void> delete(@RequestParam String date, @RequestParam String time) {
        reservationService.delete(date, time);
        return ResponseEntity.noContent().build();
    }
}
