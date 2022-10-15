package nextstep.reservation.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.ServiceTest;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.domain.ReservationRepository;
import nextstep.reservation.ui.request.ReservationCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationServiceTest extends ServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        initReservationTable();
    }

    @DisplayName("스케줄이 이미 예약되어 있을 경우 예외가 발생한다.")
    @Test
    void scheduleAlreadyReversedException() {
        saveReserved(1L);
        ReservationCreateRequest request = new ReservationCreateRequest(1L, "최현구");

        assertThatThrownBy(() -> reservationService.create(request))
            .isExactlyInstanceOf(IllegalStateException.class)
            .hasMessage("이미 해당 스케줄에 예약이 존재합니다.");
    }

    private void saveReserved(Long scheduleId) {
        reservationRepository.save(
            new Reservation(
                scheduleId,
                LocalDate.of(2022, 10, 1),
                LocalTime.of(1, 0),
                "최현구"
            ));
    }

    @DisplayName("날짜와 시간에 삭제하려는 예약이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void reservationDeleteException() {
        assertThatThrownBy(() -> reservationService.deleteByDateTime(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0)
        )).isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("시간과 날짜에 해당하는 예약정보가 없습니다.");
    }
}
