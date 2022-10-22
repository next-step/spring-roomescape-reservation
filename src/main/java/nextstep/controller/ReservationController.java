package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.domain.ReservationRequest;
import nextstep.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    ResponseEntity<String> createReservations(@RequestBody ReservationRequest reservationRequest) throws URISyntaxException {
        long id = reservationService.create(reservationRequest.getDate(), reservationRequest.getTime(), reservationRequest.getName());
        return ResponseEntity.created(new URI("/reservations/" + id)).build();
    }

    @GetMapping
    ResponseEntity<List<Reservation>> getReservations(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok().body(reservationService.findReservationsByDate(localDate));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteReservations(@RequestParam String date, @RequestParam String time) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        reservationService.deleteByLocalDateAndLocalTime(localDate, localTime);
        return ResponseEntity.noContent().build();
    }
}
