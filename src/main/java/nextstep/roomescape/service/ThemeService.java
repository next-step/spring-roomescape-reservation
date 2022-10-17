package nextstep.roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import nextstep.roomescape.core.domain.ScheduleRepository;
import nextstep.roomescape.core.domain.ThemeRepository;
import nextstep.roomescape.core.exception.ThemeException;
import nextstep.roomescape.web.dto.ThemeRequest;
import nextstep.roomescape.web.dto.ThemeResponse;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    public ThemeService(ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> findAll() {
        return themeRepository.findAll().stream()
            .map(ThemeResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    public int create(ThemeRequest request) {
        return themeRepository.create(request.toEntity());
    }

    public void deleteById(Integer id) {
        if (scheduleRepository.existsAliveByThemeId(id)) {
            throw new ThemeException("진행 중인 스케줄이 존재하는 테마는 제거할 수 없습니다.");
        }
        if (!themeRepository.existsById(id)) {
            throw new ThemeException("존재하지 않는 테마입니다.");
        }
        themeRepository.deleteById(id);
    }
}
