package nextstep.core.theme.out;

import nextstep.core.theme.Theme;

import java.util.List;

public interface ThemeRepository {
    Theme save(Theme theme);

    List<Theme> findAll();

    void deleteById(String themeId);
}
