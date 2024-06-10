package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadThemeResponse;
import roomescape.admin.dto.SaveThemeRequest;
import roomescape.admin.entity.Theme;
import roomescape.admin.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository){
        this.themeRepository = themeRepository;
    }

    public List<ReadThemeResponse> readTheme() {
        List<Theme> reservationTime = this.themeRepository.findAll();

        return ReadThemeResponse.from(reservationTime);
    }

    public ReadThemeResponse saveTheme(SaveThemeRequest saveThemeRequest) {
        Long id = this.themeRepository.save(saveThemeRequest);
        Theme theme = this.themeRepository.findById(id);

        return ReadThemeResponse.from(theme);
    }

    public void deleteTheme(Long id) {
        this.themeRepository.delete(id);
    }
}
