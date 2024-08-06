package roomescape.core.domain.theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Theme save(Theme theme);

    Optional<Theme> findById(ThemeId themeId);

    List<Theme> findAll();
}
