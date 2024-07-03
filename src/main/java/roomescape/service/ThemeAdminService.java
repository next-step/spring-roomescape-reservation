package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Theme;
import roomescape.model.ThemeCreateRequestDto;
import roomescape.model.ThemeCreateResponseDto;
import roomescape.model.ThemeReadResponseDto;
import roomescape.respository.ThemeDAO;

import java.util.List;

@Service
public class ThemeAdminService {
    private final ThemeDAO themeDAO;

    public ThemeAdminService(ThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    public ThemeCreateResponseDto createTheme(ThemeCreateRequestDto themeCreateDto) {
        Theme theme = new Theme(themeCreateDto.getName(), themeCreateDto.getDescription(),
                themeCreateDto.getThumbnail());
        Theme insertedTheme = themeDAO.insertTheme(theme);
        return new ThemeCreateResponseDto(insertedTheme.getId(), insertedTheme.getName(),
                insertedTheme.getDescription(),
                insertedTheme.getThumbnail());
    }

    public List<ThemeReadResponseDto> readThemes() {
        List<Theme> themes = themeDAO.readThemes();
        return themes.stream().map(theme -> new ThemeReadResponseDto(theme.getId(), theme.getName(),
                theme.getDescription(), theme.getThumbnail())).toList();
    }

    public void deleteTheme(Long id) {
        themeDAO.deleteTheme(id);
    }
}
