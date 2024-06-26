package roomescape.application.service.component.reader;

import org.springframework.stereotype.Component;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeRepository;
import roomescape.error.exception.NotFoundDomainException;

@Component
public class ThemeReader {

    private final ThemeRepository repository;

    public ThemeReader(ThemeRepository repository) {
        this.repository = repository;
    }

    public Theme readById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> NotFoundDomainException.notFoundTheme(id));
    }
}
