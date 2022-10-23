package nextstep.domain.schedule.model;

import java.util.*;
import java.util.stream.Collectors;

public class ScheduleMemoryRepository implements ScheduleRepository {
    private static final List<Schedule> schedules = new ArrayList<>();

    @Override
    public Long create(Schedule schedule) {
        Schedule scheduleWithId = schedule.withId();

        schedules.add(scheduleWithId);

        return scheduleWithId.getId();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return schedules.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst();
    }

    @Override
    public List<Schedule> findAllByDate(String date) {
        return schedules.stream()
            .filter(it -> Objects.equals(it.getDate().toString(), date))
            .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findAllByThemeIdAndDate(Long themeId, String date) {
        return schedules.stream()
            .filter(it -> Objects.equals(it.getThemeId(), themeId) && Objects.equals(it.getDate().toString(), date))
            .collect(Collectors.toList());
    }

    @Override
    public void removeById(Long id) {
        schedules.removeIf(it -> Objects.equals(it.getId(), id));
    }
}
