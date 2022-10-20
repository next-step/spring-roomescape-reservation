package nextstep.core.theme;

import java.util.List;

public interface ThemeRepository {
    Theme save(Theme theme);

    List<Theme> findAll();

    void deleteById(String themeId);
}
