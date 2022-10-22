package nextstep.app.web.schedule;

import nextstep.core.schedule.in.ScheduleResponse;
import nextstep.core.schedule.in.ScheduleUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/schedules")
@RestController
class ScheduleController {
    private final ScheduleUseCase useCase;

    public ScheduleController(ScheduleUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateWebRequest request) {
        ScheduleResponse schedule = useCase.create(request.to());
        return ResponseEntity
                .created(URI.create("/schedules/" + schedule.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ScheduleWebResponse>> list(
            @RequestParam Long themeId,
            @RequestParam String date
    ) {
        List<ScheduleResponse> schedules = useCase.list(themeId, LocalDate.parse(date));
        return ResponseEntity.ok(
                schedules.stream()
                        .map(ScheduleWebResponse::from)
                        .toList()
        );
    }
}
