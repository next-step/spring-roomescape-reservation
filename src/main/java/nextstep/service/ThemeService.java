package nextstep.service;

import nextstep.domain.ReservationRepository;
import nextstep.domain.ScheduleRepository;
import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    public static final String DUPLICATE_THEME_MESSAGE = "이미 존재하는 테마이름입니다.";
    public static final String CANT_DELETE_THEME = "예약이 존재하는 테마는 삭제할 수 없습니다.";

    private final ThemeRepository themes;
    private final ScheduleRepository schedules;
    private final ReservationRepository reservations;

    public ThemeService(ThemeRepository themes, ScheduleRepository schedules, ReservationRepository reservations) {
        this.themes = themes;
        this.schedules = schedules;
        this.reservations = reservations;
    }

    public Long createTheme(ThemeCreateRequest themeCreateRequest) {
        String name = themeCreateRequest.getName();
        String desc = themeCreateRequest.getDesc();
        int price = themeCreateRequest.getPrice();

        checkThemeAvailable(name);
        Theme theme = themes.save(new Theme(name, desc, price));
        return theme.getId();
    }

    private void checkThemeAvailable(String name) {
        if (themes.existsByName(name)) {
            throw new IllegalArgumentException(DUPLICATE_THEME_MESSAGE);
        }
    }

    public ThemeFindAllResponse findAllThemes() {
        List<Theme> findThemes = themes.findAll();
        return ThemeFindAllResponse.from(findThemes);
    }

    public void deleteTheme(Long themeId) {
        checkDeleteAvailable(themeId);
        themes.deleteById(themeId);
    }

    private void checkDeleteAvailable(Long themeId) {
        List<Long> scheduleIds = schedules.findIdsByThemeId(themeId);
        if (scheduleIds.stream().anyMatch(reservations::existsByScheduleId)) {
            throw new IllegalArgumentException(CANT_DELETE_THEME);
        }
    }
}
