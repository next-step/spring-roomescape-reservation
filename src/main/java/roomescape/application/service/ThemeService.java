package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.port.in.ThemeUseCase;
import roomescape.application.port.out.ThemePort;
import roomescape.domain.Theme;
import roomescape.exception.NotFoundThemeException;

@Transactional
@Service
public class ThemeService implements ThemeUseCase {

  private final ThemePort themePort;

  public ThemeService(ThemePort themePort) {
    this.themePort = themePort;
  }

  @Override
  public Theme registerTheme(Theme theme) {
    return themePort.saveTheme(theme);
  }

  @Override
  public List<Theme> retrieveThemes() {
    return themePort.findThemes();
  }

  @Override
  public void deleteTheme(Long id) {
    if (themePort.countThemeById(id) == 0) {
      throw new NotFoundThemeException();
    }

    themePort.deleteTheme(id);
  }
}
