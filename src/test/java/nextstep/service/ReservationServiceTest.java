package nextstep.service;

import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import nextstep.dto.ScheduleCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.Constants.*;
import static nextstep.service.ReservationService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationServiceTest extends ServiceTest {
    @BeforeEach
    void setUp() {
        initScheduleTable();
        initReservationTable();
        scheduleService.createSchedule(new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING));
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void createReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);

        // when
        Long reservationId = reservationService.createReservation(request);

        // then
        assertThat(reservationId).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 스케줄에 예약을 시도하면, 예외를 반환한다.")
    void noSuchSchedule() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(2L, NAME);

        // when, then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_SUCH_SCHEDULE_MESSAGE);
    }

    @Test
    @DisplayName("동시간대에 예약이 존재할 경우, 예외를 반환한다.")
    void failToCreateReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        reservationService.createReservation(request);

        // when, then
        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DUPLICATE_RESERVATION_MESSAGE);
    }

    @Test
    @DisplayName("특정 날짜에 해당하는 예약 목록을 조회한다.")
    void findAllReservations() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        reservationService.createReservation(request);

        // when
        ReservationFindAllResponse reservations = reservationService.findAllReservations(DATE_STRING);

        // then
        assertThat(reservations.getReservations()).hasSize(1);
    }

    @Test
    @DisplayName("특정 날짜와 시간에 해당하는 예약을 삭제한다.")
    void deleteReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        reservationService.createReservation(request);

        // when, then
        assertDoesNotThrow(() -> reservationService.deleteReservation(DATE_STRING, TIME_STRING));
    }

    @Test
    @DisplayName("해당 날짜, 시간에 예약이 존재하지 않는 경우, 삭제에 실패한다.")
    void failToDelete() {
        // given, when, then
        assertThatThrownBy(() -> reservationService.deleteReservation(DATE_STRING, TIME_STRING))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_RESERVATION_MESSAGE);
    }
}
