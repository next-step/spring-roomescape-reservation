package roomescape.domain.theme.domain;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Theme getById(ThemeId themeId);

    Theme save(Theme theme);

    Optional<Theme> findById(ThemeId themeId);

    List<Theme> findAll();

    List<Theme> findNotDeletedThemes();

    List<Theme> findAllByIds(List<ThemeId> themeIds);
}
