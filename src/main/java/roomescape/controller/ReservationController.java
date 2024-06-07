package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.repository.ReservationRepository;

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

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/find/reservation/list")
    public ResponseEntity<List<ReservationDto>> list() {
        List<ReservationDto> list = reservationRepository.findReservations();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create/reservation")
    public ResponseEntity<ReservationDto> create(@RequestBody ReservationDto dto) {
        Long id = reservationRepository.create(dto);
        ReservationDto reservationDto = reservationRepository.findReservationByid(id);
        return ResponseEntity.created(URI.create("/reservation/" + reservationDto.getId())).build();
    }

    @DeleteMapping("/delete/reservation/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        reservationRepository.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
