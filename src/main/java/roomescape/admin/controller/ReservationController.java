package roomescape.admin.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping(value = "/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReadReservationResponse>> readReservation(){
        List<ReadReservationResponse> response = this.reservationService.readReservation();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ReadReservationResponse> saveReservation(@Valid @RequestBody SaveReservationRequest saveReservationRequest){
        ReadReservationResponse response = this.reservationService.saveReservation(saveReservationRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id){
        this.reservationService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}