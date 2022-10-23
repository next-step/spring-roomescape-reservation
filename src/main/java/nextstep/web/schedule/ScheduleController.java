package nextstep.web.schedule;

import nextstep.domain.schedule.service.ScheduleService;
import nextstep.web.schedule.dto.ScheduleCreateRequest;
import nextstep.web.schedule.dto.ScheduleWebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateRequest request) {
        Long id = scheduleService.create(request.toEntity());

        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleWebResponse>> findAllByThemeIdAndDate(@RequestParam Long themeId, @RequestParam String date) {
        List<ScheduleWebResponse> responses = scheduleService.findAllByThemeIdAndDate(themeId, date).stream()
            .map(ScheduleWebResponse::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        scheduleService.remove(id);

        return ResponseEntity.noContent().build();
    }
}
