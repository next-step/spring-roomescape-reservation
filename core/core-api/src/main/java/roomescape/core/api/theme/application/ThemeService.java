package roomescape.core.api.theme.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.core.api.theme.application.request.ThemeAppendRequest;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;
import roomescape.core.domain.theme.ThemeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> findAll() {
        return themeRepository.findNotDeletedThemes();
    }

    public Theme appendTheme(final ThemeAppendRequest request) {
        return themeRepository.save(request.toTheme());
    }

    public void deleteTheme(final ThemeId themeId) {
        final Theme theme = themeRepository.getById(themeId);
        final Theme deleted = theme.delete();
        themeRepository.save(deleted);
    }
}
