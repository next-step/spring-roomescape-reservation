package roomescape.domain.theme.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;
import roomescape.domain.theme.domain.ThemeRepository;
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
        return themeJdbcRepository.findAllByDeleted(false);
    }

    @Override
    public List<Theme> findAllByIds(final List<ThemeId> themeIds) {
        final List<Long> themeIdValues = themeIds.stream().map(ThemeId::value).toList();
        return themeJdbcRepository.findAllByIds(themeIdValues);
    }
}
