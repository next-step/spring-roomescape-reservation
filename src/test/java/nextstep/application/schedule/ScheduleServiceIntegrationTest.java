package nextstep.application.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.application.schedule.dto.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@SpringBootTest
@DisplayName("스케쥴 서비스 통합 테스트")
class ScheduleServiceIntegrationTest {

  @Autowired
  private ScheduleService sut;

  @Test
  void 스케쥴_생성한다() {
    //given
    var schedule = Schedule.builder()
        .themeId(1L)
        .date(LocalDate.now())
        .time(LocalTime.parse("13:00"))
        .build();
    //when
    var actual = sut.create(schedule);
    //then
    assertThat(actual).isNotNull();
  }
}
