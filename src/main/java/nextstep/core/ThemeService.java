package nextstep.core;

import nextstep.app.web.dto.ThemeCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThemeService {
    private final ThemeRepository repository;

    public ThemeService(ThemeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Theme save(ThemeCreateRequest request) {
        return repository.save(request.to());
    }
}
