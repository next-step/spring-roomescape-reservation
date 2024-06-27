package roomescape.domain.theme;


import java.util.Optional;

public interface ThemeRepository {

    Theme save(Theme theme);

    Optional<Theme> findById(Long id);

    Themes findAll();

    void delete(Long id);
}
