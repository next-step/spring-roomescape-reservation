package roomescape.admin.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.service.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService){
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReadReservationTimeResponse>> readReservationTime(){
        List<ReadReservationTimeResponse> response = this.reservationTimeService.readReservationTime();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ReadReservationTimeResponse> saveReservationTime(@Valid @RequestBody SaveReservationTimeRequest saveReservationTimeRequest){
        ReadReservationTimeResponse response = this.reservationTimeService.saveReservationTime(saveReservationTimeRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(name = "id") Long id){
        this.reservationTimeService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}