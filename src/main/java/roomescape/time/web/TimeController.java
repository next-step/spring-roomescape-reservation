package roomescape.time.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.time.domain.Time;
import roomescape.time.service.TimeService;
import roomescape.time.web.dto.TimeRequest;
import roomescape.time.web.dto.TimeResponse;

import java.util.List;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/admin/time")
    public String time() {
        return "/admin/time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest request) {
        Time time = timeService.save(request.toEntity());
        return ResponseEntity.ok().body(new TimeResponse(time));
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<Time> times = timeService.findAll();
        List<TimeResponse> responses = times.stream().map(TimeResponse::new).toList();
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
