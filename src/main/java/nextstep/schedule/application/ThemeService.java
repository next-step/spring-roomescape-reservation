package nextstep.schedule.application;

import java.util.List;
import nextstep.schedule.domain.Theme;
import nextstep.schedule.domain.ThemeRepository;
import nextstep.schedule.ui.request.ThemeCreateRequest;
import nextstep.schedule.ui.response.ThemeResponse;
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
        int deletedCount = themeRepository.deleteById(themeId);
        if (deletedCount == 0) {
            throw new IllegalArgumentException("ID 에 해당하는 테마가 없습니다.");
        }
    }
}
