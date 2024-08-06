package roomescape.core.api.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
