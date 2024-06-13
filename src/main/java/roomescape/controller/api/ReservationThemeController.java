package roomescape.controller.api;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationThemeRequest;
import roomescape.dto.response.ReservationThemeResponse;
import roomescape.service.ReservationThemeService;

@RestController
@RequestMapping("/themes")
public class ReservationThemeController {

    private final ReservationThemeService reservationThemeService;

    public ReservationThemeController(ReservationThemeService reservationThemeService) {
        this.reservationThemeService = reservationThemeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationThemeResponse>> findAllReservationThemes() {
        return ResponseEntity.ok().body(reservationThemeService.findAllReservationThemes());
    }

    @PostMapping
    public ResponseEntity<ReservationThemeResponse> createReservationTheme(
            @Valid @RequestBody ReservationThemeRequest reservationThemeRequest) {
        ReservationThemeResponse newReservationTheme = reservationThemeService.createReservationTheme(
                reservationThemeRequest);

        return ResponseEntity
                .created(URI.create("/themes/" + newReservationTheme.getId()))
                .body(newReservationTheme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTheme(@PathVariable(value = "id") Long id) {
        reservationThemeService.deleteReservationTheme(id);

        return ResponseEntity.noContent().build();
    }
}
