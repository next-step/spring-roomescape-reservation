package nextstep.service;

import nextstep.dto.ScheduleCreateRequest;
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
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2022-12-01", "12:01");

        // when
        Long scheduleId = scheduleService.createSchedule(request);

        // then
        assertThat(scheduleId).isNotNull();
    }
}
