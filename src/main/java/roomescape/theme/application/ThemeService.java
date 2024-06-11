package roomescape.theme.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.theme.domain.Theme;
import roomescape.theme.domain.repository.ThemeRepository;
import roomescape.theme.dto.ThemeCreateRequest;
import roomescape.theme.dto.ThemeResponse;
import roomescape.theme.exception.ThemeNotFoundException;

@Service
public class ThemeService {

    private final ThemeRepository jdbcThemeRepository;

    public ThemeService(ThemeRepository jdbcThemeRepository) {
        this.jdbcThemeRepository = jdbcThemeRepository;
    }

    public ThemeResponse createTheme(ThemeCreateRequest request) {
        Theme theme = jdbcThemeRepository.save(request.toTheme());
        return ThemeResponse.from(theme);
    }

    public List<ThemeResponse> getThemes() {
        List<Theme> themes = jdbcThemeRepository.findAll();
        return themes.stream()
                .map(ThemeResponse::from)
                .toList();
    }

    public void deleteTheme(Long themeId) {
        if (!jdbcThemeRepository.existsById(themeId)) {
            throw new ThemeNotFoundException("존재하지 않는 테마입니다.");
        }
        jdbcThemeRepository.deleteById(themeId);
    }
}
