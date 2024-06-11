package roomescape.reservation.presentation.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.reservation.presentation.dto.ReservationResponse;
import roomescape.reservation.service.ReservationService;
import roomescape.theme.domain.Theme;
import roomescape.theme.service.ThemeService;
import roomescape.time.domain.Time;
import roomescape.time.service.TimeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ApiReservationController {

    private final ReservationService reservationService;
    private final TimeService timeService;
    private final ThemeService themeService;

    public ApiReservationController(ReservationService reservationService, TimeService timeService, ThemeService themeService) {
        this.reservationService = reservationService;
        this.timeService = timeService;
        this.themeService = themeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<Reservation> reservations = reservationService.findAll();
        List<ReservationResponse> responses = reservations.stream().map(reservation ->
                        ReservationResponse.builder().
                                id(reservation.getId())
                                .name(reservation.getName())
                                .date(reservation.getDate())
                                .time(reservation.getTime())
                                .theme(reservation.getTheme()
                                ).build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest request) {
        Time time = timeService.findById(request.getTimeId());
        Theme theme = themeService.findById(request.getThemeId());
        Reservation reservation = reservationService.save(new Reservation(null, request.getName(), request.getDate(), time, theme));
        return ResponseEntity.ok().body(
                ReservationResponse.builder()
                        .id(reservation.getId())
                        .name(reservation.getName())
                        .date(reservation.getDate())
                        .time(reservation.getTime())
                        .theme(reservation.getTheme()
                        ).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
