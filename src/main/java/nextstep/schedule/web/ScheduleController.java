package nextstep.schedule.web;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.schedule.web.request.ListScheduleRequest;
import nextstep.schedule.web.request.MakeScheduleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleDao scheduleDao;

    public ScheduleController(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @PostMapping
    ResponseEntity<Void> makeSchedule(@RequestBody MakeScheduleRequest requestBody) {
        Long id = scheduleDao.insert(requestBody.toSchedule());
        URI locationUri = URI.create("/schedules/" + id);
        return ResponseEntity.created(locationUri)
            .build();
    }

    @GetMapping
    ResponseEntity<List<Schedule>> listSchedules(@ModelAttribute ListScheduleRequest requestParams) {
        List<Schedule> schedules = scheduleDao.findBy(
            requestParams.getThemeId(),
            LocalDate.parse(requestParams.getDate())
        );
        return ResponseEntity.ok(schedules);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleDao.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
