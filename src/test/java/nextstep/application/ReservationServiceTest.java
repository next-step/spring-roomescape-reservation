package nextstep.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationServiceTest extends ServiceTest {

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        initReservationTable();
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
