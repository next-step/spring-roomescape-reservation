package roomescape.theme;

import org.springframework.stereotype.Service;
import roomescape.entities.Theme;
import roomescape.repositories.ThemeRepository;
import roomescape.theme.data.ThemeAddRequestDto;

import java.util.List;

@Service
public class ThemeService {
    final private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> findAllThemes() {
        return themeRepository.findAllThemes();
    }

    public Theme save(ThemeAddRequestDto themeAddRequestDto){
        Theme theme = new Theme(
          themeAddRequestDto.getName(),
          themeAddRequestDto.getDescription(),
          themeAddRequestDto.getThumbnail()
        );

        return themeRepository.save(theme);
    }

    public void deleteThemeById(Long id){
        themeRepository.deleteById(id);
    }
}
