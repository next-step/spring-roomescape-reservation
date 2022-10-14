package nextstep.theme;

import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeJdbcRepository themeJdbcRepository;

    public ThemeService(ThemeJdbcRepository themeJdbcRepository) {
        this.themeJdbcRepository = themeJdbcRepository;
    }


    public Theme createTheme(ThemeCreateRequest request) {
        return this.themeJdbcRepository.create(request.toObject());
    }
}
