package nextstep.web.endpoint.reservation.controller;

import lombok.extern.slf4j.Slf4j;
import nextstep.web.endpoint.reservation.request.ReservationCreateRequest;
import nextstep.web.endpoint.reservation.request.ReservationDeleteRequest;
import nextstep.web.endpoint.reservation.request.ReservationsSearchRequest;
import nextstep.web.endpoint.reservation.response.ReservationResponse;
import nextstep.web.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody ReservationCreateRequest request) {
        log.info("예약 생성 요청. request= {}", request);
        Long id = reservationService.create(request);
        URI location = URI.create("/reservations/" + id);
        log.info("예약 생성 응답. location= {}", location);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAllByDate(ReservationsSearchRequest request) {
        log.info("예약 조회 요청. request= {}", request);
        List<ReservationResponse> response = reservationService.findAllByDate(request);
        log.info("예약 조회 응답. response= {}", response);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations")
    public ResponseEntity<Void> cancel(ReservationDeleteRequest request) {
        log.info("예약 삭제 요청. request= {}", request);
        reservationService.cancelByDateAndTime(request);
        log.info("예약 삭제 성공.");

        return ResponseEntity.noContent().build();
    }
}
