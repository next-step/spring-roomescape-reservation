package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.dto.theme.create.ThemeCreateResponse;
import roomescape.mapper.ThemeMapper;
import roomescape.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> findThemes() {
        return themeRepository.findThemes()
                .stream()
                .map(theme -> ThemeMapper.toDto(theme))
                .collect(Collectors.toList());
    }

    public ThemeCreateResponse createTheme(ThemeCreateRequest request) {
        Theme entity = ThemeMapper.toEntity(request);
        Theme theme = themeRepository.createTheme(entity);
        return ThemeCreateResponse.toResponse(theme);
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteTheme(id);
    }
}
