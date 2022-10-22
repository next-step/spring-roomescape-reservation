package nextstep.domain.schedule.service;

import nextstep.domain.mock.MockTheme;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.exception.UnableDeleteScheduleException;
import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.model.ScheduleRepository;
import nextstep.domain.theme.exception.NotFoundThemeException;
import nextstep.domain.theme.model.ThemeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScheduleDomainServiceTest {
    private final ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);
    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final ThemeRepository themeRepository = mock(ThemeRepository.class);

    @DisplayName("스케쥴 생성 시")
    @Nested
    class CreateScheduleTest {

        @DisplayName("존재하지 않는 테마일 경우 실패한다")
        @Test
        void create_fail_not_exist_theme() {
            // given
            Long themeId = 1L;
            LocalDate date = LocalDate.of(2022, 8, 11);
            LocalTime time = LocalTime.of(11, 10);
            when(themeRepository.findById(any())).thenReturn(Optional.empty());

            ScheduleDomainService sut = new ScheduleDomainService(scheduleRepository, reservationRepository, themeRepository);

            // when, then
            assertThatThrownBy(() -> sut.create(themeId, date, time))
                    .isInstanceOf(NotFoundThemeException.class)
                    .hasMessageContaining("테마 정보를 찾을 수 없습니다.");
        }

        @DisplayName("스케쥴 생성을 성공한다")
        @Test
        void create_success() {
            // given
            Long themeId = 1L;
            LocalDate date = LocalDate.of(2022, 8, 11);
            LocalTime time = LocalTime.of(11, 10);
            when(themeRepository.findById(any())).thenReturn(Optional.of(MockTheme.from()));

            ScheduleDomainService sut = new ScheduleDomainService(scheduleRepository, reservationRepository, themeRepository);

            // when
            Schedule actual = sut.create(themeId, date, time);

            // then
            verify(scheduleRepository, times(1)).save(any());
        }
    }

    @DisplayName("스케쥴 삭제 시")
    @Nested
    class CancelReservationsTest {
        @DisplayName("예약이 존재할 시 실패한다")
        @Test
        void delete_by_id_fail() {
            // given
            Long scheduleId = 1L;
            when(reservationRepository.existByScheduleId(any())).thenReturn(true);

            ScheduleDomainService sut = new ScheduleDomainService(scheduleRepository, reservationRepository, themeRepository);

            // when, then
            assertThatThrownBy(() -> sut.deleteById(scheduleId))
                    .isInstanceOf(UnableDeleteScheduleException.class)
                    .hasMessageContaining("예약이 있는 스케쥴은 삭제할 수 없습니다.");
        }

        @DisplayName("스케쥴을 삭제한다")
        @Test
        void delete_by_id() {
            // given
            Long scheduleId = 1L;
            when(reservationRepository.existByScheduleId(any())).thenReturn(false);

            ScheduleDomainService sut = new ScheduleDomainService(scheduleRepository, reservationRepository, themeRepository);

            // when, then
            assertDoesNotThrow(() -> {
                sut.deleteById(scheduleId);
            });
            verify(scheduleRepository, times(1)).deleteById(any());
        }
    }
}