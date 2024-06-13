package roomescape.time;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public Time add(@RequestBody Time time) {
        return timeService.add(time);
    }

    @GetMapping
    public List<Time> reservationTimes() {
        return timeService.reservationTimes();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        timeService.delete(id);
    }
}
