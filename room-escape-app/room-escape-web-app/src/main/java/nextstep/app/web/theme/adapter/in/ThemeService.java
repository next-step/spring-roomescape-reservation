package nextstep.app.web.theme.adapter.in;

import nextstep.core.theme.Theme;
import nextstep.core.theme.in.ThemeCreateRequest;
import nextstep.core.theme.in.ThemeResponse;
import nextstep.core.theme.in.ThemeUseCase;
import nextstep.core.theme.out.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ThemeService implements ThemeUseCase {
    private final ThemeRepository repository;

    public ThemeService(ThemeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ThemeResponse create(ThemeCreateRequest request) {
        Objects.requireNonNull(request);
        Theme theme = request.to();

        Theme saved = repository.save(theme);
        return ThemeResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<ThemeResponse> list() {
        return repository.findAll().stream()
                .map(ThemeResponse::from)
                .toList();
    }

    @Transactional
    public void delete(String themeId) {
        Objects.requireNonNull(themeId);

        repository.deleteById(themeId);
    }
}
