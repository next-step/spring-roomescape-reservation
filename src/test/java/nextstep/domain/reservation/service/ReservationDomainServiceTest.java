package nextstep.domain.reservation.service;

import nextstep.domain.mock.MockSchedule;
import nextstep.domain.reservation.exception.ExistReservationException;
import nextstep.domain.reservation.exception.NotFoundReservationException;
import nextstep.domain.reservation.model.Reservation;
import nextstep.domain.reservation.model.ReservationRepository;
import nextstep.domain.schedule.exception.NotFoundScheduleException;
import nextstep.domain.schedule.model.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationDomainServiceTest {
    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);
    private final ScheduleRepository scheduleRepository = mock(ScheduleRepository.class);

    @DisplayName("예약 생성 시")
    @Nested
    class CreateReservationTest {

        @DisplayName("존재하지 않는 스케쥴일 경우 실패한다")
        @Test
        void create_fail_not_exist_schedule() {
            // given
            Long scheduleId = 1L;
            LocalDate existDate = LocalDate.of(2022, 8, 11);
            LocalTime existTime = LocalTime.of(11, 10);
            when(scheduleRepository.findById(any())).thenReturn(Optional.empty());

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when, then
            assertThatThrownBy(() -> sut.create(scheduleId, existDate, existTime, "신지혜"))
                    .isInstanceOf(NotFoundScheduleException.class)
                    .hasMessageContaining("스케쥴 정보를 찾을 수 없습니다.");
        }

        @DisplayName("스케쥴에 예약이 이미 있는 경우 실패한다")
        @Test
        void create_fail_exist_date_and_time() {
            // given
            Long scheduleId = 1L;
            LocalDate existDate = LocalDate.of(2022, 8, 11);
            LocalTime existTime = LocalTime.of(11, 10);
            when(scheduleRepository.findById(any())).thenReturn(Optional.of(MockSchedule.from()));
            when(reservationRepository.existByScheduleId(any())).thenReturn(true);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when, then
            assertThatThrownBy(() -> sut.create(scheduleId, existDate, existTime, "신지혜"))
                    .isInstanceOf(ExistReservationException.class)
                    .hasMessageContaining("해당 스케쥴에 예약이 이미 존재합니다.");
        }

        @DisplayName("예약을 성공한다")
        @Test
        void create_success() {
            // given
            Long scheduleId = 1L;
            LocalDate date = LocalDate.of(2022, 8, 11);
            LocalTime time = LocalTime.of(11, 10);
            String name = "신지혜";
            when(scheduleRepository.findById(any())).thenReturn(Optional.of(MockSchedule.from()));
            when(reservationRepository.existByDateTime(any(), any())).thenReturn(false);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when
            Reservation actual = sut.create(scheduleId, date, time, name);

            // then
            verify(reservationRepository, times(1)).save(any());
        }
    }

    @DisplayName("예약 목록 조회 시")
    @Nested
    class FindReservationsTest {
        @DisplayName("날짜를 기준으로 예약 목록을 조회한다")
        @Test
        void findAllByDate() {
            // given
            LocalDate existDate = LocalDate.of(2022, 8, 11);
            Reservation reservation1 = new Reservation(1L, 1L, existDate, LocalTime.of(11, 10), "신지혜");
            Reservation reservation2 = new Reservation(2L, 2L, existDate, LocalTime.of(12, 10), "지혜신");
            when(reservationRepository.findAllByDate(any())).thenReturn(List.of(reservation1, reservation2));

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when
            List<Reservation> actual = sut.findAllByDate(existDate);

            // then
            verify(reservationRepository, times(1)).findAllByDate(any());
            assertThat(actual).hasSize(2);
        }


        @DisplayName("날짜를 기준으로 예약 목록을 조회한다 - 예약이 존재하지 않을 시 빈 값")
        @Test
        void findAllByEmpty() {
            // given
            LocalDate date = LocalDate.of(2022, 8, 11);
            when(reservationRepository.findAllByDate(any())).thenReturn(List.of());

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when
            List<Reservation> actual = sut.findAllByDate(date);

            // then
            verify(reservationRepository, times(1)).findAllByDate(any());
            assertThat(actual).hasSize(0);
        }
    }

    @DisplayName("예약 취소 시")
    @Nested
    class CancelReservationsTest {
        @DisplayName("날짜, 시간에 예약이 존재하지 않을 시 실패한다")
        @Test
        void cancel_by_datetime_fail() {
            // given
            LocalDate date = LocalDate.of(2022, 8, 11);
            LocalTime time = LocalTime.of(11, 10);
            when(reservationRepository.existByDateTime(any(), any())).thenReturn(false);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when, then
            assertThatThrownBy(() -> sut.cancelByDateTime(date, time))
                    .isInstanceOf(NotFoundReservationException.class)
                    .hasMessageContaining("예약 정보를 찾을 수 없습니다.");
        }

        @DisplayName("날짜, 시간을 기준으로 예약을 취소한다")
        @Test
        void cancel_by_datetime() {
            // given
            LocalDate existDate = LocalDate.of(2022, 8, 11);
            LocalTime existTime = LocalTime.of(11, 10);
            when(reservationRepository.existByDateTime(any(), any())).thenReturn(true);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository, scheduleRepository);

            // when, then
            assertDoesNotThrow(() -> {
                sut.cancelByDateTime(existDate, existTime);
            });
            verify(reservationRepository, times(1)).deleteByDateTime(any(), any());
        }
    }
}
