package nextstep.service;

import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.Constants.*;
import static nextstep.service.ThemeService.CANT_DELETE_THEME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ThemeServiceTest extends ServiceTest {
    @BeforeEach
    void setUp() {
        initThemeTable();
        initScheduleTable();
    }

    @Test
    @DisplayName("테마를 생성한다.")
    void createTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);

        // when
        Long themeId = themeService.createTheme(request);

        // then
        assertThat(themeId).isNotNull();
    }

    @Test
    @DisplayName("전체 테마목록을 조회한다.")
    void findAllThemes() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        themeService.createTheme(request);

        // when
        ThemeFindAllResponse themes = themeService.findAllThemes();

        // then
        assertThat(themes.getThemes()).hasSize(1);
    }

    @Test
    @DisplayName("테마 ID로 테마를 삭제한다.")
    void deleteTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        Long themeId = themeService.createTheme(request);

        // when, then
        assertDoesNotThrow(() -> themeService.deleteTheme(themeId));
    }

    @Test
    @DisplayName("테마 삭제 시, 예약이 존재하면 예외를 반환한다.")
    void failToDelete() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        Long themeId = themeService.createTheme(request);

        // when
        scheduleService.createSchedule(new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING));
        reservationService.createReservation(new ReservationCreateRequest(SCHEDULE_ID, NAME));

        // then
        assertThatThrownBy(() -> themeService.deleteTheme(themeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CANT_DELETE_THEME);
    }
}
