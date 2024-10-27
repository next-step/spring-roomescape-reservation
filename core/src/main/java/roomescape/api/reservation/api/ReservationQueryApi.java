package roomescape.api.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.api.reservation.api.response.ReservationQueryHttpResponse;
import roomescape.api.reservation.application.ReservationQueryService;
import roomescape.api.reservation.application.dto.ReservationTimeThemeDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationQueryApi {

    private final ReservationQueryService queryService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationQueryHttpResponse>> fetchAll() {
        final List<ReservationTimeThemeDto> reservationTimeThemeDtos = queryService.fetchReservationThemes();
        final List<ReservationQueryHttpResponse> response = ReservationQueryHttpResponse.from(reservationTimeThemeDtos);
        return ResponseEntity.ok().body(response);
    }
}
