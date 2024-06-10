package roomescape.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.service.ReservationTimeService;

import java.util.List;

@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService){
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReadReservationTimeResponse>> readReservationTime(){
        List<ReadReservationTimeResponse> response = this.reservationTimeService.readReservationTime();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/times")
    public ResponseEntity<ReadReservationTimeResponse> saveReservationTime(@RequestBody SaveReservationTimeRequest saveReservationTimeRequest){
        ReadReservationTimeResponse response = this.reservationTimeService.saveReservationTime(saveReservationTimeRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(name = "id") Long id){
        this.reservationTimeService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }
}