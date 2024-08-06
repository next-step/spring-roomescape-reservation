package roomescape.db.core.theme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;
import roomescape.core.domain.theme.ThemeRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ThemeEntityRepository implements ThemeRepository {

    private final ThemeJdbcRepository themeJdbcRepository;

    @Override
    public Theme save(final Theme theme) {
        final ThemeEntity themeEntity = ThemeEntity.fromModel(theme);
        ThemeEntity saved = themeJdbcRepository.save(themeEntity);
        return saved.toModel();
    }

    @Override
    public Optional<Theme> findById(final ThemeId themeId) {
        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        return themeJdbcRepository.findAll().stream()
                .map(ThemeEntity::toModel)
                .toList();
    }
}
