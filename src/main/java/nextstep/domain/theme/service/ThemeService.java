package nextstep.domain.theme.service;

import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository repository;

    public ThemeService(ThemeRepository repository) {
        this.repository = repository;
    }

    public Long create(Theme theme) {
        return repository.create(theme);
    }

    public List<ThemeResponse> findAll() {
        return repository.findAll().stream()
            .map(ThemeResponse::new)
            .collect(Collectors.toList());
    }

    public void remove(Long id) {
        repository.remove(id);
    }
}
