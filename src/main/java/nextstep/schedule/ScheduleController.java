package nextstep.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleCreateRequest request) {
        ScheduleResponse response = scheduleService.createSchedule(request);
        return ResponseEntity.created(URI.create("/schedules/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules(@RequestParam("themeId") Long themeId, @RequestParam("date") String date) {
        List<ScheduleResponse> scheduleResponses = scheduleService.getSchedules(themeId, LocalDate.parse(date));
        return ResponseEntity.ok(scheduleResponses);
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId) {
        this.scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
