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
import roomescape.entities.Theme;
import roomescape.reservation.data.ReservationAddRequestDto;
import roomescape.reservation.data.ReservationResponseDto;
import roomescape.reservationtime.ReservationTimeService;
import roomescape.theme.ThemeService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;
    private final ThemeService themeService;

    public ReservationController(ReservationService reservationService,
                                 ReservationTimeService reservationTimeService,
                                 ThemeService themeService){
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAllReservations(){
        List<ReservationResponseDto> res = reservationService.findAllReservations();
        return ResponseEntity.ok().body(res);
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(
      @RequestBody ReservationAddRequestDto reservationAddRequestDto) {
        ReservationTime reservationTime = reservationTimeService.findById(
          reservationAddRequestDto.getTimeId());
        Theme theme = themeService.findById(reservationAddRequestDto.getThemeId());

        Reservation reservation = reservationService.saveReservation(
          new Reservation(reservationAddRequestDto.getName(),
            reservationAddRequestDto.getDate(),
            reservationTime,
            theme));

        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancelReservation(@PathVariable("id") Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
}
