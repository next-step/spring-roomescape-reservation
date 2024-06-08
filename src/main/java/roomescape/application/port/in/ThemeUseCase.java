package roomescape.application.port.in;

import java.util.List;
import roomescape.domain.Theme;

public interface ThemeUseCase {

  Theme registerTheme(Theme theme);

  List<Theme> retrieveThemes();

  void deleteTheme(Long id);
}
