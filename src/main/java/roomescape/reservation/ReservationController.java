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
import roomescape.reservation.data.ReservationAddRequestDto;
import roomescape.reservationTime.ReservationTimeService;

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
    public ResponseEntity<List<Reservation>> searchReservations(){
        List<Reservation> res = reservationService.searchAllReservations();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationAddRequestDto reservationAddRequestDto) {
        ReservationTime reservationTime = reservationTimeService.findByTime(reservationAddRequestDto.getTime());
        Reservation reservation = reservationService.addReservation(new Reservation(reservationAddRequestDto.getName(), reservationAddRequestDto.getDate(), reservationTime));
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
    }
}
