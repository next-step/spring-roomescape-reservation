package roomescape.time.presentation.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.Time;
import roomescape.time.presentation.dto.TimeRequest;
import roomescape.time.presentation.dto.TimeResponse;
import roomescape.time.service.TimeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ApiTimeController {

    private final TimeService timeService;

    public ApiTimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping()
    public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest request) {
        Time time = timeService.save(request.convertToDomainObject());
        TimeResponse build = TimeResponse.builder()
                .id(time.getId())
                .startAt(time.getStartAt())
                .build();
        return ResponseEntity.ok().body(build);
    }

    @GetMapping()
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<Time> times = timeService.findAll();
        List<TimeResponse> responses = times.stream().map(
                time -> TimeResponse.builder()
                        .id(time.getId())
                        .startAt(time.getStartAt())
                        .build()
        ).toList();
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
