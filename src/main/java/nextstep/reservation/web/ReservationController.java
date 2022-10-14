package nextstep.reservation.web;

import java.net.URI;
import java.util.List;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.web.request.CancelReservationRequest;
import nextstep.reservation.web.request.ListReservationRequest;
import nextstep.reservation.web.request.MakeReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @PostMapping
    ResponseEntity<Void> makeReservation(@RequestBody MakeReservationRequest requestBody) {
        // TODO ggyool 아이디 받아오면 수정해야함
        URI locationUri = URI.create("/reservations/1");
        return ResponseEntity.created(locationUri)
            .build();
    }

    @GetMapping
    ResponseEntity<List<Reservation>> listReservations(@ModelAttribute ListReservationRequest requestParams) {
        return null;
    }

    @DeleteMapping
    ResponseEntity<Void> cancelReservation(@ModelAttribute CancelReservationRequest requestParams) {
        return ResponseEntity.noContent()
            .build();
    }
}
