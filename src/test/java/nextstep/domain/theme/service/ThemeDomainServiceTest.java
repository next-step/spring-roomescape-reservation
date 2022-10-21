package nextstep.domain.theme.service;

import nextstep.domain.mock.MockTheme;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.exception.UnableDeleteThemeException;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.model.ThemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ThemeDomainServiceTest {
    private final ThemeRepository themeRepository = mock(ThemeRepository.class);
    private final ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);

    @DisplayName("테마 생성을 성공한다")
    @Test
    void create_success() {
        // given
        String name = "신지혜";
        String desc = "서얼명";
        Long price = 1000L;
        when(themeRepository.save(any())).thenReturn(MockTheme.from(name, desc, price));

        ThemeDomainService sut = new ThemeDomainService(themeRepository, scheduleRepository);

        // when
        Theme actual = sut.create(name, desc, price);

        // then
        verify(themeRepository, times(1)).save(any());
    }

    @DisplayName("테마 삭제 시")
    @Nested
    class CancelReservationsTest {
        @DisplayName("스케쥴에 등록된 테마는 삭제 실패한다")
        @Test
        void cancel_by_datetime_fail() {
            // given
            Long themeId = 1L;
            when(scheduleRepository.existByThemId(any())).thenReturn(true);

            ThemeDomainService sut = new ThemeDomainService(themeRepository, scheduleRepository);

            // when, then
            assertThatThrownBy(() -> sut.delete(themeId))
                    .isInstanceOf(UnableDeleteThemeException.class)
                    .hasMessageContaining("스케쥴에 등록된 테마는 삭제할 수 없습니다.");
        }

        @DisplayName("삭제 성공한다")
        @Test
        void cancel_by_datetime() {
            // given
            Long themeId = 1L;
            when(scheduleRepository.existByThemId(any())).thenReturn(false);

            ThemeDomainService sut = new ThemeDomainService(themeRepository, scheduleRepository);

            // when, then
            assertDoesNotThrow(() -> {
                sut.delete(themeId);
            });
            verify(themeRepository, times(1)).deleteById(any());
        }
    }
}