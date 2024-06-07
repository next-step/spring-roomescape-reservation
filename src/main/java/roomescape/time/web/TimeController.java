package roomescape.time.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.time.domain.Time;
import roomescape.time.service.TimeService;
import roomescape.time.web.dto.TimeRequest;
import roomescape.time.web.dto.TimeResponse;

@Controller
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/admin/time")
    public String time() {
        return "/admin/time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<TimeResponse> save(@RequestBody TimeRequest request) {
        Time time = timeService.save(request.toEntity());
        return ResponseEntity.ok().body(new TimeResponse(time));
    }
}
