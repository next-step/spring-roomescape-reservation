package nextstep.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleRepositoryTest {
    @Autowired
    private ScheduleRepository schedules;

    @Test
    @DisplayName("스케줄을 저장한다.")
    void save() {
        // given
        LocalDate date = LocalDate.of(2022, 12, 1);
        LocalTime time = LocalTime.of(12, 1);
        Schedule schedule = new Schedule(1L, date, time);

        // when
        Schedule savedSchedule = schedules.save(schedule);

        // then
        assertThat(schedule.getId()).isNotNull();
        assertThat(savedSchedule).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(schedule);
    }
}
