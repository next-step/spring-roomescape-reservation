package roomescape.domain.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.api.response.ReservationQueryHttpResponse;
import roomescape.domain.reservation.application.ReservationQueryService;
import roomescape.domain.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.global.rest.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationQueryApi {

    private final ReservationQueryService queryService;

    @GetMapping("/reservations")
    public ApiResponse<List<ReservationQueryHttpResponse>> fetchAll() {
        final List<ReservationTimeThemeDto> reservationTimeThemeDtos = queryService.fetchReservationThemes();
        return ApiResponse.ok(ReservationQueryHttpResponse.from(reservationTimeThemeDtos));
    }
}
