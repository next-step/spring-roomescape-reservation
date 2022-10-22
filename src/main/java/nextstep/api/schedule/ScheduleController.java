package nextstep.api.schedule;

import nextstep.api.schedule.dto.ScheduleCreateRequest;
import nextstep.domain.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateRequest request) {
        Long id = scheduleService.create(request.toEntity());

        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }
}
