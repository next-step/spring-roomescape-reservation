package nextstep.app.web.schedule.port.web;

import nextstep.app.web.schedule.application.dto.CreateScheduleResult;
import nextstep.app.web.schedule.application.usecase.CreateScheduleUseCase;
import nextstep.app.web.schedule.application.usecase.DeleteScheduleUseCase;
import nextstep.app.web.schedule.port.query.ScheduleQuery;
import nextstep.domain.schedule.domain.model.Schedule;
import nextstep.domain.theme.domain.model.Theme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class ScheduleController {
    private final CreateScheduleUseCase createScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final ScheduleQuery scheduleQuery;

    public ScheduleController(CreateScheduleUseCase createScheduleUseCase, DeleteScheduleUseCase deleteScheduleUseCase, ScheduleQuery scheduleQuery) {
        this.createScheduleUseCase = createScheduleUseCase;
        this.deleteScheduleUseCase = deleteScheduleUseCase;
        this.scheduleQuery = scheduleQuery;
    }


    @PostMapping("/schedules")
    public ResponseEntity<Void> create(@RequestBody ScheduleCreateRequest request) {
        CreateScheduleResult result = createScheduleUseCase.create(request.toCommand());
        return ResponseEntity.created(URI.create("/schedules/" + result.id())).build();
    }

    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteScheduleUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> findAll(@RequestParam Long themeId, @RequestParam String date) {
        Map<Schedule, Theme> scheduleAndThemes = scheduleQuery.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok(ScheduleResponse.listOf(scheduleAndThemes));
    }
}
