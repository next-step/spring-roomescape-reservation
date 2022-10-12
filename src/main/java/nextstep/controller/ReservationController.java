package nextstep.controller;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private Long tmpId;
    private final List<Reservation> reservations;

    public ReservationController() {
        this.tmpId = 1L;
        this.reservations = new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        LocalDate date = LocalDate.parse(reservationCreateRequest.getDate());
        LocalTime time = LocalTime.parse(reservationCreateRequest.getTime());
        String name = reservationCreateRequest.getName();

        checkReservationAvailable(date, time);

        Reservation reservation = new Reservation(tmpId++, date, time, name);
        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).build();
    }

    @GetMapping
    public ResponseEntity<ReservationFindAllResponse> findAll(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Reservation> findReservations = reservations.stream()
                .filter(it -> it.equalsDate(parsedDate))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ReservationFindAllResponse.from(findReservations));
    }

    private void checkReservationAvailable(LocalDate date, LocalTime time) {
        if (reservations.stream()
                .anyMatch(it -> it.equalsDateAndTime(date, time))) {
            throw new IllegalArgumentException("동시간대에 이미 예약이 존재합니다.");
        }
    }
}
