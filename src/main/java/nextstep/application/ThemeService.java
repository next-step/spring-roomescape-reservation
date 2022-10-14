package nextstep.application;

import java.util.List;
import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.ui.request.ThemeCreateRequest;
import nextstep.ui.response.ThemeResponse;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemeResponse create(ThemeCreateRequest request) {
        Theme theme = new Theme(request.getName(), request.getDesc(), request.getPrice());
        return ThemeResponse.from(themeRepository.save(theme));
    }

    public List<ThemeResponse> findAll() {
        return ThemeResponse.of(themeRepository.findAll());
    }

    public void deleteById(Long themeId) {
        int count = themeRepository.deleteById(themeId);
        if (count == 0) {
            throw new IllegalArgumentException("ID 에 해당하는 테마가 없습니다.");
        }
    }
}
