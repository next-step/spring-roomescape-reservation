package nextstep.controller;

import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nextstep.controller.dto.ScheduleViewRequest;
import nextstep.domain.schedule.ScheduleRepository;
import nextstep.domain.schedule.dto.ScheduleCommandDto.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleCommandController {

  ScheduleRepository scheduleRepository;

  @PostMapping
  public ResponseEntity<Void> createSchedule(@Valid @RequestBody ScheduleViewRequest.Create createReq) {
    scheduleRepository.save(createReq.toDomainCommand());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(@Valid @PathVariable Long id) {
    scheduleRepository.delete(Delete.builder()
        .id(id)
        .build()
    );
    return ResponseEntity.ok().build();
  }
}
