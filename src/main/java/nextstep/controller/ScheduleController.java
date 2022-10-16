package nextstep.controller;

import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import nextstep.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        Long scheduleId = scheduleService.createSchedule(scheduleCreateRequest);
        return ResponseEntity.created(URI.create("/schedules/" + scheduleId)).build();
    }

    @GetMapping
    public ResponseEntity<ScheduleFindAllResponse> findAll(@RequestParam Long themeId, @RequestParam String date) {
        ScheduleFindAllResponse scheduleFindAllResponse = scheduleService.findAllSchedules(themeId, date);
        return ResponseEntity.ok(scheduleFindAllResponse);
    }
}
