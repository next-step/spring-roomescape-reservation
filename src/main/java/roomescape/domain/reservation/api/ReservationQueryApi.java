package roomescape.domain.reservation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.reservation.api.response.ReservationQueryHttpResponse;
import roomescape.domain.reservation.service.ReservationQueryService;
import roomescape.domain.reservation.service.response.ReservationQueryResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationQueryApi {

    private final ReservationQueryService queryService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationQueryHttpResponse>> fetchAll() {
        final List<ReservationQueryResponse> reservations = queryService.findAll();
        final List<ReservationQueryHttpResponse> response = ReservationQueryHttpResponse.from(reservations);
        return ResponseEntity.ok().body(response);
    }

}
