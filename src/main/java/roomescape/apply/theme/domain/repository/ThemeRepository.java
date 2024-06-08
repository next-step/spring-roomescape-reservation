package roomescape.apply.theme.domain.repository;

import roomescape.apply.theme.domain.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {

    Theme save(Theme reservationTime);

    List<Theme> findAll();

    void deleteById(Long id);

    Optional<Long> checkIdExists(long id);

    Optional<Theme> findById(long themeId);
}
