package roomescape.core.api.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.core.api.theme.service.request.ThemeAppendRequest;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme appendTheme(final ThemeAppendRequest request) {
        return themeRepository.save(request.toTheme());
    }
}
