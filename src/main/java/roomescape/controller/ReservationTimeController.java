package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

import java.net.URI;
import java.util.List;

@Controller
@ResponseBody
public class ReservationTimeController {

    ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDto> addTime(@RequestBody ReservationTimeDto dto) {
        ReservationTimeDto time = reservationTimeRepository.addTime(dto);
        return ResponseEntity.ok().body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> timeList() {
        List<ReservationTime> list = reservationTimeRepository.findTimeList();
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeRepository.deleteTime(id);
        return ResponseEntity.ok().build();
    }
}
