package roomescape.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/admin/reservation")
    public String reservation(){
        return BASE_PATH+"/reservation-legacy.html";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReadReservationResponse>> readReservation(){
        List<ReadReservationResponse> response = this.adminService.readReservation();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/reservations")
    public ResponseEntity<List<ReadReservationResponse>> saveReservation(@RequestBody SaveReservationRequest saveReservationRequest){
        List<ReadReservationResponse> response = this.adminService.saveReservation(saveReservationRequest);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id){
        this.adminService.deleteReservation(id);

        return ResponseEntity.ok().build();
    }
}
