package nextstep.app.web.ui;

import nextstep.app.web.dto.ScheduleCreateWebRequest;
import nextstep.app.web.dto.ScheduleWebResponse;
import nextstep.core.schedule.Schedule;
import nextstep.core.schedule.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/schedules")
@RestController
public class ScheduleController {
    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateWebRequest request) {
        Schedule schedule = service.create(request.to());
        return ResponseEntity
                .created(URI.create("/schedules/" + schedule.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleWebResponse>> list(
            @RequestParam Long themeId,
            @RequestParam String date
    ) {
        List<Schedule> schedules = service.list(themeId, LocalDate.parse(date));
        return ResponseEntity.ok(
                schedules.stream()
                        .map(ScheduleWebResponse::from)
                        .toList()
        );
    }
}
