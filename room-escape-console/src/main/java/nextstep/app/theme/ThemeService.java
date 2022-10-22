package nextstep.app.theme;

import nextsetp.domain.theme.Theme;
import nextsetp.domain.theme.ThemeRepository;

import java.util.List;

public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void save(String name, String desc, Long price) {
        themeRepository.save(new Theme(name, desc, price));
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public void delete(Long reservationId) {
        themeRepository.delete(reservationId);
    }
}
