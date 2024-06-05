package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ReservationController {

    private static final HashMap<Long, Reservation> reservations = new HashMap<>();
    private final Reservation reservation1 = new Reservation("제이슨", "2023-08-05", "15:40");
    private final Reservation reservation2 = new Reservation("심슨", "2023-08-05", "15:40");

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        reservations.put(reservation1.getId(), reservation1);
        reservations.put(reservation2.getId(), reservation2);

        final List<ReservationResponseDto> reservationResponseDtos = reservations.values().stream()
                .map(
                        reservation -> new ReservationResponseDto.Builder()
                                .id(reservation.getId())
                                .name(reservation.getName())
                                .date(reservation.getDate())
                                .time(reservation.getTime())
                                .build()
                ).toList();

        return ResponseEntity.ok().body(reservationResponseDtos);

    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = new Reservation(
                        reservationRequestDto.getName(),
                        reservationRequestDto.getDate(),
                        reservationRequestDto.getTime());
        reservations.put(reservation.getId(), reservation);
        final Reservation savedReservation = reservations.get(reservation.getId());

        ReservationResponseDto responseDto = new ReservationResponseDto.Builder()
                .id(savedReservation.getId())
                .name(savedReservation.getName())
                .date(savedReservation.getDate())
                .time(savedReservation.getTime())
                .build();

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        if (!reservations.containsKey(id)) {
            return ResponseEntity.notFound().build();
        } else {
            reservations.remove(id);
            return ResponseEntity.ok().build();
        }
    }
}
