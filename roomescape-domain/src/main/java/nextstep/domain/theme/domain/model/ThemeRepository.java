package nextstep.domain.theme.domain.model;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    Theme save(Theme theme);

    List<Theme> findAll();

    void deleteById(Long id);

    void deleteAll();

    Optional<Theme> findByName(String name);
}
