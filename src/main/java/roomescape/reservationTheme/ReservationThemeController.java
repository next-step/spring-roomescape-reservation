package roomescape.reservationTheme;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ReservationThemeController {

    private final ReservationThemeService reservationThemeService;

    public ReservationThemeController(ReservationThemeService reservationThemeService) {
        this.reservationThemeService = reservationThemeService;
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ReservationThemeResponseDto>> getThemes() {
        final List<ReservationThemeResponseDto> themese = reservationThemeService.getThemes();
        return ResponseEntity.ok(themese);
    }

    @PostMapping("/themes")
    public ResponseEntity<ReservationThemeResponseDto> createTheme(@RequestBody ReservationThemeRequestDto requestDto) {
        final ReservationThemeResponseDto theme = reservationThemeService.createTheme(requestDto);
        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/theme/" + theme.getId());
        return new ResponseEntity<>(theme, header, HttpStatus.CREATED);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        reservationThemeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }
}
