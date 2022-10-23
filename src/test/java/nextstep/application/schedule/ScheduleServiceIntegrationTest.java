package nextstep.application.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  @Test
  void 동일시각에_동일한_테마를_가지는_스케쥴을_생성시_예외가_발생한다() {
    //given
    var schedule = Schedule.builder()
        .themeId(1L)
        .date(LocalDate.now())
        .time(LocalTime.parse("13:00"))
        .build();
    sut.create(schedule);
    //when
    //then
    assertThatThrownBy(() -> sut.create(schedule))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
