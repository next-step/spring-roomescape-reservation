package roomescape.domain.theme;


public interface ThemeRepository {

    Theme save(Theme theme);

    Theme findById(Long themeId);

    Themes findAll();

    void delete(Long themeId);
}
