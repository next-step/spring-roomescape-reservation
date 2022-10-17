package nextstep.schedule.web;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import nextstep.common.exception.NotFoundException;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.persistence.ScheduleDao;
import nextstep.schedule.web.reponse.ListScheduleResponse;
import nextstep.schedule.web.request.ListScheduleRequest;
import nextstep.schedule.web.request.MakeScheduleRequest;
import nextstep.theme.domain.Theme;
import nextstep.theme.persistence.ThemeDao;
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
    private final ThemeDao themeDao;

    public ScheduleController(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    @PostMapping
    ResponseEntity<Void> makeSchedule(@RequestBody MakeScheduleRequest requestBody) {
        Long id = scheduleDao.insert(requestBody.toSchedule());
        URI locationUri = URI.create("/schedules/" + id);
        return ResponseEntity.created(locationUri)
            .build();
    }

    @GetMapping
    ResponseEntity<List<ListScheduleResponse>> listSchedules(@ModelAttribute ListScheduleRequest requestParams) {
        List<Schedule> schedules = scheduleDao.findBy(
            requestParams.getThemeId(),
            LocalDate.parse(requestParams.getDate())
        );

        Long themeId = requestParams.getThemeId();
        Theme theme = themeDao.findById(themeId)
            .orElseThrow(() -> new NotFoundException(String.format("아이디가 [%s]인 테마를 찾을 수 없습니다.", themeId)));

        List<ListScheduleResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            responses.add(new ListScheduleResponse(
                schedule.getId(),
                theme,
                schedule.getDate(),
                schedule.getTime()
            ));
        }
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleDao.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
