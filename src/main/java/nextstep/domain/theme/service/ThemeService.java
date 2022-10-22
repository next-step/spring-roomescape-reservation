package nextstep.domain.theme.service;

import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository repository;

    public ThemeService(ThemeRepository repository) {
        this.repository = repository;
    }

    public Long create(Theme theme) {
        return repository.create(theme);
    }

    public List<Theme> findAll() {
        return repository.findAll();
    }
}
