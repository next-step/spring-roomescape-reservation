package roomescape.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.service.ReservationTimeService;

import java.util.List;

@Controller
public class ReservationTimeController {
    private static final String BASE_PATH = "/admin";

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService){
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/time")
    public String time(){
        return BASE_PATH+"/time.html";
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