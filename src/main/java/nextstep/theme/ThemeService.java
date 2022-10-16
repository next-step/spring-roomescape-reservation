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
            throw new IllegalStateException("테마 생성 실패: 이미 존재하는 테마 이름입니다.");
        }
        return this.themeJdbcRepository.create(request.toObject());
    }

    public List<ThemeResponse> getThemes() {
        return this.themeJdbcRepository.findAll().stream()
                .map(ThemeResponse::from)
                .toList();
    }

    public void deleteTheme(Long themeId) {
        boolean result = this.themeJdbcRepository.deleteThemeById(themeId);
        if (!result) {
            throw new IllegalStateException("테마 삭제에 실패했습니다.: 해당 ID의 테마가 존재하지 않음");
        }
    }
}
