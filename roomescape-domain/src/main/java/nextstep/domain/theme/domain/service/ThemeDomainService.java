package nextstep.domain.theme.domain.service;

import nextstep.domain.theme.domain.model.Theme;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class ThemeDomainService {
    private final ThemeRepository themeRepository;

    public ThemeDomainService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Long create(String name, String description, Long price) {
        Theme theme = new Theme(null, name, description, price);

        if (themeRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("동일한 이름의 테마가 존재합니다.");
        }

        Theme saved = themeRepository.save(theme);
        return saved.id();
    }

    public void deleteById(Long id) {
        themeRepository.deleteById(id);
    }
}
