package nextstep.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ScheduleViewResponse;
import nextstep.domain.schedule.Schedule;
import nextstep.domain.schedule.ScheduleRepository;
import nextstep.domain.schedule.dto.ScheduleFindCondition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleQueryController {

  ScheduleRepository scheduleRepository;

  @GetMapping
  public ResponseEntity<List<ScheduleViewResponse.Schedule>> getSchedules() {
    List<Schedule> schedules = scheduleRepository.findAll(ScheduleFindCondition.EMPTY);
    List<ScheduleViewResponse.Schedule> responses = schedules.stream()
        .map(ScheduleViewResponse.Schedule::of)
        .toList();
    return ResponseEntity.ok().body(responses);
  }
}
