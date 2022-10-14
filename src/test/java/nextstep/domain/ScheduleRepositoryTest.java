package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScheduleRepositoryTest extends RepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        initScheduleTable();
        initThemeTable();
    }

    @DisplayName("스케줄 저장")
    @Test
    void save() {
        Theme theme = saveTheme();
        Schedule schedule = new Schedule(
            theme.getId(),
            LocalDate.of(2022, 10, 1),
            LocalTime.of(12, 0)
        );

        Schedule saved = scheduleRepository.save(schedule);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(schedule);
    }

    @DisplayName("테마 ID 와 날짜에 해당되는 모든 스케줄 조회")
    @Test
    void findAllByThemeIdAndDate() {
        Theme theme = saveTheme();
        Schedule schedule = saveSchedule(
            theme.getId(),
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 1)
        );

        List<Schedule> schedules = scheduleRepository.findAllByThemeIdAndDate(
            theme.getId(),
            LocalDate.of(2022, 10, 1)
        );

        assertThat(schedules).containsExactly(schedule);
    }

    @DisplayName("스케줄 삭제")
    @Test
    void deleteById() {
        Theme theme = saveTheme();
        Schedule schedule = saveSchedule(
            theme.getId(),
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 1)
        );

        int count = scheduleRepository.deleteById(schedule.getId());

        assertThat(count).isEqualTo(1);
        assertThat(scheduleRepository.findAllByThemeIdAndDate(
            theme.getId(),
            LocalDate.of(2022, 10, 1)
        )).isEmpty();
    }

    private Theme saveTheme() {
        return themeRepository.save(new Theme("비밀의방", "재밌당", BigDecimal.valueOf(50_000)));
    }

    private Schedule saveSchedule(Long themeId, LocalDate date, LocalTime time) {
        return scheduleRepository.save(new Schedule(themeId, date, time));
    }
}
