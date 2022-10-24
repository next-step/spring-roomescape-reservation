package nextstep.application.schedule;

import lombok.RequiredArgsConstructor;
import nextstep.application.schedule.dto.Schedule;
import nextstep.application.themes.ThemeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleCreateThemeNotExistValidation implements ScheduleCreateValidation {

  private final ThemeService themeService;

  @Override
  public void checkValid(Schedule schedule) {
    var theme = themeService.getTheme(schedule.themeId());
    if (theme.isEmpty()) {
      throw new IllegalArgumentException(String.format("테마가 존재하지 않습니다. 테마ID: %s", schedule.themeId()));
    }

  }

  @Override
  public int priority() {
    return 2;
  }
}
