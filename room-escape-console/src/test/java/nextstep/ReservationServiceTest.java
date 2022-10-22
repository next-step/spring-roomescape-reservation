package nextstep;

import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.exception.DuplicationReservationException;
import nextstep.app.reservation.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class ReservationServiceTest {
    private ReservationService reservationService;
    String testDate = "2022-08-11";
    String testTime = "13:00";

    @BeforeEach
    void setUp() {
        reservationService = Configuration.getReservationService();
        reservationService.save(testDate, testTime, "a");
    }

    @DisplayName("해당 날짜의 예약을 조회한다.")
    @Test
    void findAllBy() {
        List<Reservation> reservations = reservationService.findAllBy(testDate);
        Assertions.assertThat(reservations).hasSize(1);
    }

    @DisplayName("예약을 생성한다.")
    @Test
    void save() {
        String date = "2022-01-01";
        String time = "00:00";
        reservationService.save(date, time, "a");
        Assertions.assertThat(reservationService.findAllBy(date)).hasSize(1);
    }

    @DisplayName("해당 날짜와 시간의 예약을 삭제한다.")
    @Test
    void delete() {
        String time = "00:00";
        reservationService.save(testDate, time, "a");
        reservationService.delete(testDate, time);
        Assertions.assertThat(reservationService.findAllBy(testDate)).hasSize(1);
    }

    @DisplayName("같은 날 같은 시간에 예약은 중복 생성 할 수 없다.")
    @Test
    void checkSaveDuplicated() {
        String duplicateDate = "2022-08-11";
        String duplicateTime = "13:00";

        Assertions.assertThatThrownBy(() -> {
            reservationService.save(duplicateDate, duplicateTime, "a");
        }).isInstanceOf(DuplicationReservationException.class);
    }
}