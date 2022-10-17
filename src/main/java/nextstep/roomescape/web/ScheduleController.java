package nextstep.roomescape.web;

import java.net.URI;
import java.util.List;
import nextstep.roomescape.service.ScheduleService;
import nextstep.roomescape.web.dto.ScheduleRequest;
import nextstep.roomescape.web.dto.ScheduleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<URI> create(@RequestBody ScheduleRequest request) {
        final int id = scheduleService.create(request);
        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }

    @DeleteMapping("/schedules")
    public ResponseEntity<Void> delete(Integer id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
