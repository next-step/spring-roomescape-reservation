package nextstep.theme;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeJdbcRepository themeJdbcRepository;

    public ThemeService(ThemeJdbcRepository themeJdbcRepository) {
        this.themeJdbcRepository = themeJdbcRepository;
    }


    public Theme createTheme(ThemeCreateRequest request) {
        if (this.themeJdbcRepository.existByName(request.getName())) {
            throw new IllegalStateException("이미 존재하는 테마 이름입니다.");
        }
        return this.themeJdbcRepository.create(request.toObject());
    }

    public List<ThemeResponse> getThemes() {
        return this.themeJdbcRepository.findAll().stream()
                .map(ThemeResponse::from)
                .toList();
    }
}
