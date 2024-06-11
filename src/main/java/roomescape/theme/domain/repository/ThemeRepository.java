package roomescape.theme.domain.repository;

import java.util.List;
import java.util.Optional;

import roomescape.theme.domain.Theme;

public interface ThemeRepository {

    Theme save(Theme theme);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);
}
