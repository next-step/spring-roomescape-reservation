package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@ResponseBody
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository,
                                 ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> list() {
        List<ReservationDto> list = reservationRepository.findReservations();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto dto) {
        ReservationTimeDto timeDto = reservationTimeRepository.findReservationTimeById(dto.getTimeId());
        Reservation reservation = reservationRepository.create(dto, timeDto);
        System.out.println(reservation);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reservationRepository.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
