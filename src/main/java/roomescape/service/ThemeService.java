package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ThemeRq;
import roomescape.dto.ThemeRs;
import roomescape.repository.ThemeRepo;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepo themeRepo;

    public ThemeService(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }

    public List<ThemeRs> getAllThemes() {
        return themeRepo.findAll().stream()
                .map(theme -> new ThemeRs(
                        theme.getId(),
                        theme.getName(),
                        theme.getDescription(),
                        theme.getThumbnail()
                ))
                .toList();
    }

    public ThemeRs addTheme(ThemeRq themeRq) {
        Long id = themeRepo.save(themeRq);
        return new ThemeRs(id, themeRq.getName(), themeRq.getDescription(), themeRq.getThumbnail());
    }

    public void deleteTheme(Long id) {
        themeRepo.deleteById(id);
    }
}
