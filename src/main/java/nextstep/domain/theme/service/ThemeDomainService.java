package nextstep.domain.theme.service;

import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.exception.NotFoundThemeException;
import nextstep.domain.theme.exception.UnableDeleteThemeException;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeDomainService {
    private final ThemeRepository themeRepository;
    private final ScheduleRepository scheduleRepository;

    public ThemeDomainService(ThemeRepository themeRepository, ScheduleRepository scheduleRepository) {
        this.themeRepository = themeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Theme create(String name, String desc, Long price) {
        return themeRepository.save(new Theme(name, desc, price));
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundThemeException();
                });
    }

    public void delete(Long id) {
        if (scheduleRepository.existByThemId(id)) {
            throw new UnableDeleteThemeException("스케쥴에 등록된 테마는 삭제할 수 없습니다.");
        }
        themeRepository.deleteById(id);
    }
}
