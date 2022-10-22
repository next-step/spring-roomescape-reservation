package nextstep.ui;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.ScheduleService;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.schedule.dto.ScheduleRes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

  private final ScheduleService service;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Schedule schedule) {
    var id = service.create(schedule);
    return ResponseEntity.created(URI.create("/schedules/" + id)).build();
  }

  @GetMapping
  public ResponseEntity<List<ScheduleRes>> getSchedules(@RequestParam Long themeId,
      @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate date) {
    var schedules = service.getSchedules(themeId, date);
    return ResponseEntity.ok(schedules);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
    service.deleteSchedule(id);
    return ResponseEntity.noContent().build();
  }
}
