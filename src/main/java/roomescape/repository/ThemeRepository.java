package roomescape.repository;

import roomescape.domain.Theme;

import java.util.List;

public interface ThemeRepository {
    List<Theme> findThemes();

    Theme createTheme(Theme entity);

    void deleteTheme(Long id);
}
