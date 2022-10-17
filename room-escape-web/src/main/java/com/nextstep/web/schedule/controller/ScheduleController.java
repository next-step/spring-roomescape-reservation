package com.nextstep.web.schedule.controller;

import com.nextstep.web.schedule.dto.CreateScheduleRequest;
import com.nextstep.web.schedule.dto.ScheduleResponse;
import com.nextstep.web.schedule.service.ScheduleCommandService;
import com.nextstep.web.schedule.service.ScheduleQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleQueryService scheduleQueryService;
    private final ScheduleCommandService scheduleCommandService;

    public ScheduleController(ScheduleQueryService scheduleQueryService, ScheduleCommandService scheduleCommandService) {
        this.scheduleQueryService = scheduleQueryService;
        this.scheduleCommandService = scheduleCommandService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> read(@RequestParam Long themeId, @RequestParam String date) {
        return ResponseEntity.ok(scheduleQueryService.findAllBy(themeId, LocalDate.parse(date)));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateScheduleRequest request) {
        Long id = scheduleCommandService.save(request);
        return ResponseEntity.created(URI.create("/reservation/" + id)).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleCommandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
