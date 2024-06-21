package controller;

import domain.Reserve;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private List<Reserve> reserves = new ArrayList<>();
    private Long id = 0L;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/admin/reservation")
    public String adminReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reserve>> readReserve() {
        return ResponseEntity.ok().body(reserves);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reserve> addReserve(@RequestBody Reserve reserve) {
        reserve.setId(id++);
        reserves.add(reserve);
        return ResponseEntity.created(URI.create("api/reserve/" + reserve.getId())).body(reserve);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReserve(@PathVariable Long id) {
        reserves.remove(id);
        return ResponseEntity.ok().build();
    }
}
