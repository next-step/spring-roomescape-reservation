package nextstep.reservation.web;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import nextstep.common.exception.NotFoundException;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.exception.DuplicatedReservationException;
import nextstep.reservation.persistence.ReservationStorage;
import nextstep.reservation.web.request.CancelReservationRequest;
import nextstep.reservation.web.request.ListReservationRequest;
import nextstep.reservation.web.request.MakeReservationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationStorage reservationStorage;

    public ReservationController(ReservationStorage reservationStorage) {
        this.reservationStorage = reservationStorage;
    }

    @PostMapping
    ResponseEntity<Void> makeReservation(@RequestBody MakeReservationRequest requestBody) {
        validateDuplication(requestBody);

        Long id = reservationStorage.insert(requestBody.toReservation());
        URI locationUri = URI.create("/reservations/" + id);
        return ResponseEntity.created(locationUri)
            .build();
    }

    private void validateDuplication(MakeReservationRequest requestBody) {
        Optional<Reservation> optionalReservation = reservationStorage.findBy(
            requestBody.getScheduleId(),
            LocalDate.parse(requestBody.getDate()),
            LocalTime.parse(requestBody.getTime())
        );
        if (optionalReservation.isPresent()) {
            throw new DuplicatedReservationException(
                requestBody.getScheduleId(), requestBody.getDate(), requestBody.getTime()
            );
        }
    }

    @GetMapping
    ResponseEntity<List<Reservation>> listReservations(@ModelAttribute ListReservationRequest requestParams) {
        List<Reservation> reservations = reservationStorage.findBy(
            requestParams.getScheduleId(),
            LocalDate.parse(requestParams.getDate())
        );
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping
    ResponseEntity<Void> cancelReservation(@ModelAttribute CancelReservationRequest requestParams) {
        Long scheduleId = requestParams.getScheduleId();
        LocalDate date = LocalDate.parse(requestParams.getDate());
        LocalTime time = LocalTime.parse(requestParams.getTime());
        reservationStorage.findBy(scheduleId, date, time)
            .orElseThrow(() -> new NotFoundException(
                String.format("예약이 존재하지 않습니다. [scheduleId: %s] [date: %s] [time: %s]", scheduleId, date, time)
            ));
        reservationStorage.deleteBy(scheduleId, date, time);
        return ResponseEntity.noContent()
            .build();
    }
}
