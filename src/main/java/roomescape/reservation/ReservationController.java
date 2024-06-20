package roomescape.reservation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;
import roomescape.errors.ErrorCode;
import roomescape.exceptions.SpringRoomException;
import roomescape.reservation.data.ReservationAddRequestDto;
import roomescape.reservation.data.ReservationResponseDto;
import roomescape.reservationtime.ReservationTimeService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAllReservations(){
        List<ReservationResponseDto> res = reservationService.findAllReservations();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationAddRequestDto reservationAddRequestDto) {
        ReservationTime reservationTime = reservationTimeService.findByStartAt(reservationAddRequestDto.getTime());
        Reservation reservation = reservationService.saveReservation(new Reservation(reservationAddRequestDto.getName(), reservationAddRequestDto.getDate(), reservationTime));
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
}
