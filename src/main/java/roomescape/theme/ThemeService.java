package roomescape.theme;

import org.springframework.stereotype.Service;
import roomescape.repositories.ThemeRepository;
import roomescape.theme.data.ThemeAddRequestDto;

@Service
public class ThemeService {

    final private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void addTheme(ThemeAddRequestDto addRequestDto){
    }

    public void deleteTheme(Long id){
    }


}
