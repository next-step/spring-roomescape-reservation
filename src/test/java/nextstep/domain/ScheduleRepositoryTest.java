package nextstep.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(schedule);
    }

    @Test
    @DisplayName("테마ID와 날짜에 해당하는 스케줄내역을 조회한다.")
    void findAllByThemeIdAndDate() {
        // given
        LocalDate date = LocalDate.of(2022, 12, 2);
        LocalTime time = LocalTime.of(12, 2);
        Schedule schedule = schedules.save(new Schedule(1L, date, time));

        // when
        List<Schedule> findSchedules = schedules.findAllByThemeIdAndDate(1L, date);

        // then
        assertThat(findSchedules).hasSize(1);
        assertThat(findSchedules.get(0)).usingRecursiveComparison()
                .isEqualTo(schedule);
    }
}
