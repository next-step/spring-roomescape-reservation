package roomescape.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.ReadReservationTimeResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.service.AdminService;

import java.util.List;

@Controller
public class AdminController {

    private static final String BASE_PATH = "/admin";
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String init(){
        return BASE_PATH+"/index.html";
    }

    @GetMapping(BASE_PATH+"/reservation")
    public String reservation(){
        return BASE_PATH+"/reservation.html";
    }

    @GetMapping(BASE_PATH+"/time")
    public String time(){
        return BASE_PATH+"/time.html";
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReadReservationTimeResponse>> readReservationTime(){
        List<ReadReservationTimeResponse> response = this.adminService.readReservationTime();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/times")
    public ResponseEntity<ReadReservationTimeResponse> saveReservationTime(@RequestBody SaveReservationTimeRequest saveReservationTimeRequest){
        ReadReservationTimeResponse response = this.adminService.saveReservationTime(saveReservationTimeRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable(name = "id") Long id){
        this.adminService.deleteReservationTime(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReadReservationResponse>> readReservation(){
        List<ReadReservationResponse> response = this.adminService.readReservation();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReadReservationResponse> saveReservation(@RequestBody SaveReservationRequest saveReservationRequest){
        ReadReservationResponse response = this.adminService.saveReservation(saveReservationRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id){
        this.adminService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}