package nextstep.schedules.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.reservation.exception.AlreadyReservedException;
import nextstep.schedules.controller.request.FindScheduleRequest;
import nextstep.schedules.controller.request.ScheduleCreateRequest;
import nextstep.schedules.controller.response.FindScheduleResponse;
import nextstep.schedules.exception.ScheduleNotFoundException;
import nextstep.schedules.exception.ThemesNotFoundException;
import nextstep.schedules.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleCreateRequest request) {
        try {
            Long savedId = scheduleService.createSchedule(request.getThemeId(), request.getDate(), request.getTime());
            return ResponseEntity.created(URI.create("/schedules/" + savedId)).build();
        } catch (ThemesNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FindScheduleResponse>> findSchedule(FindScheduleRequest request) {
        List<FindScheduleResponse> response = scheduleService.findSchedule(request.getThemeId(), request.getDate())
                                                            .stream()
                                                            .map(FindScheduleResponse::from)
                                                            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeSchedule(@PathVariable Long id) {
        try {
            scheduleService.removeSchedule(id);
            return ResponseEntity.noContent().build();

        } catch (ScheduleNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (AlreadyReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
