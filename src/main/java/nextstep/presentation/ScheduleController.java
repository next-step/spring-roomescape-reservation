package nextstep.presentation;

import java.net.URI;
import java.util.List;
import nextstep.application.ScheduleService;
import nextstep.presentation.dto.ScheduleRequest;
import nextstep.presentation.dto.ScheduleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Void> make(@RequestBody ScheduleRequest request) {
        Long scheduleId = scheduleService.make(request);
        return ResponseEntity.created(URI.create("/schedules/" + scheduleId)).build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> checkAll(
        @RequestParam Long themeId,
        @RequestParam String date
    ) {
        List<ScheduleResponse> responses = scheduleService.checkAll(themeId, date);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancel(@RequestParam Long id) {
        scheduleService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
