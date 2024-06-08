package roomescape.theme.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.theme.domain.Theme;
import roomescape.theme.dto.ThemeCreateRequest;
import roomescape.theme.dto.ThemeResponse;
import roomescape.theme.infrastructure.JdbcThemeRepository;

@Service
public class ThemeService {

    private final JdbcThemeRepository jdbcThemeRepository;

    public ThemeService(JdbcThemeRepository jdbcThemeRepository) {
        this.jdbcThemeRepository = jdbcThemeRepository;
    }

    public ThemeResponse createTheme(ThemeCreateRequest request) {
        Theme theme = jdbcThemeRepository.save(request.toTheme());
        return ThemeResponse.of(theme.getId(), theme.getName(), theme.getDescription(), theme.getThumbnail());
    }

    public List<ThemeResponse> getThemes() {
        List<Theme> themes = jdbcThemeRepository.findAll();
        return themes.stream()
                .map(ThemeResponse::from)
                .toList();
    }
}
