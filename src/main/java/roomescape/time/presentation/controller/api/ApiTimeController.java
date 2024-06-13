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

    @PostMapping
    public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest request) {
        Time time = timeService.save(request.convertToDomainObject());
        return ResponseEntity.ok().body(new TimeResponse(
                time.getId(),
                time.getStartAt()));
    }

    @GetMapping
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<Time> times = timeService.findAll();
        List<TimeResponse> responses = times.stream().map(
                time -> new TimeResponse(
                        time.getId(),
                        time.getStartAt())
        ).toList();
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        timeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
