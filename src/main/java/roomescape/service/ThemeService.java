package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Theme;
import roomescape.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long addTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    public List<Theme> lookUpTheme() {
        return themeRepository.readAll();
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }
}
