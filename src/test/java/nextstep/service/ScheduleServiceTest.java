package nextstep.service;

import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("스케줄을 생성한다.")
    void createSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2021-12-01", "12:01");

        // when
        Long scheduleId = scheduleService.createSchedule(request);

        // then
        assertThat(scheduleId).isNotNull();
    }

    @Test
    @DisplayName("특정 테마와 날짜에 해당하는 스케줄 목록을 조회한다.")
    void findAllSchedules() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2021-12-02", "12:02");
        scheduleService.createSchedule(request);

        // when
        ScheduleFindAllResponse schedules = scheduleService.findAllSchedules(1L, "2021-12-02");

        // then
        assertThat(schedules.getSchedules()).hasSize(1);
    }
}
