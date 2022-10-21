package nextstep.web.endpoint.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import nextstep.web.endpoint.schedule.request.ScheduleCreateRequest;
import nextstep.web.endpoint.schedule.request.ScheduleSearchRequest;
import nextstep.web.endpoint.schedule.response.ScheduleResponse;
import nextstep.web.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedules")
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateRequest request) {
        log.info("스케쥴 생성 요청. request= {}", request);
        Long id = scheduleService.create(request);
        URI location = URI.create("/schedules/" + id);
        log.info("스케쥴 생성 응답. location= {}", location);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> findAllByCondition(ScheduleSearchRequest request) {
        log.info("스케쥴 목록 조회 요청. request= {}", request);
        List<ScheduleResponse> response = scheduleService.findAllByThemeIdAndDate(request);
        log.info("스케쥴 목록 조회 응답. response= {}", response);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> cancel(@PathVariable Long scheduleId) {
        log.info("스케쥴 삭제 요청. scheduleId= {}", scheduleId);
        scheduleService.deleteById(scheduleId);
        log.info("스케쥴 삭제 성공.");

        return ResponseEntity.noContent().build();
    }
}
