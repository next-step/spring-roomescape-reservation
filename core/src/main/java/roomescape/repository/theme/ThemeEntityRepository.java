package roomescape.repository.theme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.common.ActiveStatus;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeId;
import roomescape.domain.theme.ThemeRepository;
import roomescape.domain.theme.exception.ThemeNotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ThemeEntityRepository implements ThemeRepository {

    private final ThemeJdbcRepository themeJdbcRepository;

    @Override
    public Theme save(final Theme theme) {
        return themeJdbcRepository.save(theme);
    }

    @Override
    public Optional<Theme> findById(final ThemeId themeId) {
        return themeJdbcRepository.findById(themeId.value());
    }

    @Override
    public List<Theme> findAll() {
        return themeJdbcRepository.findAll();
    }

    @Override
    public Theme getById(final ThemeId themeId) {
        return findById(themeId).orElseThrow(() -> ThemeNotFoundException.from(themeId));
    }

    @Override
    public List<Theme> findNotDeletedThemes() {
        return themeJdbcRepository.findAllByActiveStatus(ActiveStatus.ACTIVE);
    }

    @Override
    public List<Theme> findAllByIds(final List<ThemeId> themeIds) {
        final List<Long> themeIdValues = themeIds.stream().map(ThemeId::value).toList();
        return themeJdbcRepository.findAllByIds(themeIdValues);
    }
}
