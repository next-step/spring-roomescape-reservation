package nextstep.domain.reservation.model;

import nextstep.domain.reservation.exception.ExistReservationException;
import nextstep.domain.reservation.service.ReservationDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationDomainServiceTest {
    private final ReservationRepository reservationRepository = mock(ReservationRepository.class);

    @DisplayName("예약 생성 시")
    @Nested
    class CreateReservationTest {

        @DisplayName("날짜와 시간이 똑같은 예약이 이미 있는 경우 실패한다")
        @Test
        void create_fail_exist_date_and_time() {
            // given
            LocalDate existDate = LocalDate.of(2022, 8, 11);
            LocalTime existTime = LocalTime.of(11, 10);
            when(reservationRepository.existByDateTime(any(), any())).thenReturn(true);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository);

            // when, then
            assertThatThrownBy(() -> sut.create(existDate, existTime, "신지혜"))
                    .isInstanceOf(ExistReservationException.class)
                    .hasMessageContaining("해당 일시에 이미 예약이 존재합니다.");
        }

        @DisplayName("예약을 성공한다")
        @Test
        void create_success() {
            // given
            LocalDate date = LocalDate.of(2022, 8, 11);
            LocalTime time = LocalTime.of(11, 10);
            String name = "신지혜";
            when(reservationRepository.existByDateTime(any(), any())).thenReturn(false);

            ReservationDomainService sut = new ReservationDomainService(reservationRepository);

            // when
            Reservation actual = sut.create(date, time, name);

            // then
            verify(reservationRepository, times(1)).save(any());
        }
    }

    @DisplayName("날짜를 기준으로 예약 목록을 조회한다")
    @Test
    void findAllByDate() {
        // given
        LocalDate existDate = LocalDate.of(2022, 8, 11);
        Reservation reservation1 = new Reservation(1L, existDate, LocalTime.of(11, 10), "신지혜");
        Reservation reservation2 = new Reservation(2L, existDate, LocalTime.of(12, 10), "지혜신");
        when(reservationRepository.findAllByDate(any())).thenReturn(List.of(reservation1, reservation2));

        ReservationDomainService sut = new ReservationDomainService(reservationRepository);

        // when
        List<Reservation> actual = sut.findAllByDate(existDate);

        // then
        verify(reservationRepository, times(1)).findAllByDate(any());
    }

    @DisplayName("날짜, 시간을 기준으로 예약을 취소한다")
    @Test
    void cancelByDateAndTime() {
        // given
        LocalDate existDate = LocalDate.of(2022, 8, 11);
        LocalTime existTime = LocalTime.of(11, 10);

        ReservationDomainService sut = new ReservationDomainService(reservationRepository);

        // when, then
        assertDoesNotThrow(() -> {
            sut.cancelByDateTime(existDate, existTime);
        });
        verify(reservationRepository, times(1)).deleteByDateTime(any(), any());
    }
}
