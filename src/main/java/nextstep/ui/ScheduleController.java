package nextstep.ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import nextstep.application.ScheduleService;
import nextstep.ui.request.ScheduleCreateRequest;
import nextstep.ui.response.ScheduleResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleCreateRequest request) {
        ScheduleResponse response = scheduleService.create(request);
        return ResponseEntity.created(URI.create("/schedules/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByThemeIdAndDate(
        @RequestParam Long themeId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ResponseEntity.ok(scheduleService.findAllByThemeAndDate(themeId, date));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteById(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
