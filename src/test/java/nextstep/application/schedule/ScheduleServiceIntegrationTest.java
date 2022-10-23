package nextstep.application.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.themes.ThemeService;
import nextstep.application.themes.dto.Theme;
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

  @Autowired
  private ThemeService themeService;

  @Test
  void 스케쥴_생성한다() {
    //given
    var themeId = 1L;
    var date = LocalDate.now();
    var time = LocalTime.now();
    //when
    var actual = 스케쥴_생성된다(themeId, date, time);
    //then
    assertThat(actual).isNotNull();
  }

  @Test
  void 동일시각에_동일한_테마를_가지는_스케쥴을_생성시_예외가_발생한다() {
    //given
    var themeId = 1L;
    var date = LocalDate.now();
    var time = LocalTime.now();
    var schedule = 스케쥴_생성된다(themeId, date, time);
    //when
    //then
    assertThatThrownBy(() -> sut.create(schedule))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 스케쥴_조회한다() {
    //given
    var themeId = 테마_생성된다();
    var date = LocalDate.now();
    var time = LocalTime.now();

    스케쥴_생성된다(themeId, date, time);
    스케쥴_생성된다(themeId, date, time.minus(1, ChronoUnit.HOURS));
    //when
    var actual = sut.getSchedules(themeId, date);
    //then
    assertThat(actual).hasSize(2);
  }

  private Schedule 스케쥴_생성된다(Long themeId, LocalDate date, LocalTime time) {
    var schedule = Schedule.builder()
        .themeId(themeId)
        .date(date)
        .time(time)
        .build();
    sut.create(schedule);
    return schedule;
  }

  private Long 테마_생성된다() {
    var theme = Theme.builder()
        .name("테마이름")
        .desc("테마설명")
        .price(BigDecimal.valueOf(22000))
        .build();
    return themeService.create(theme);
  }
}
