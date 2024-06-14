package roomescape.repository;

import roomescape.repository.entity.ThemeEntity;

import java.util.Optional;

public interface ThemeRepository {

    ThemeEntity save(ThemeEntity themeEntity);

    Optional<ThemeEntity> findById(Long themeId);
}
