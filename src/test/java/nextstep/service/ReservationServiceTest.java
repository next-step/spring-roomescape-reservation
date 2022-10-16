package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import nextstep.application.ReservationService;
import nextstep.exception.ReservationException;
import nextstep.presentation.dto.ReservationRequest;
import nextstep.presentation.dto.ReservationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        ReservationRequest request = new ReservationRequest("2022-08-11", "13:00", "dani");
        reservationService.make(request);
    }

    @AfterEach
    void tearDown() {
        reservationService.cancelAll();
    }

    @DisplayName("예약을 생성한다.")
    @Test
    void make_success() {
        // given
        ReservationRequest request = new ReservationRequest("2022-08-11", "14:00", "dani");

        // when
        Long reservationId = reservationService.make(request);

        // then
        assertThat(reservationId).isNotNull();
    }

    @DisplayName("예약을 생성할 때, `날짜`와 `시간`이 똑같은 예약이 있으면 예약을 생성할 수 없다.")
    @Test
    void make_fail() {
        // given
        ReservationRequest request = new ReservationRequest("2022-08-11", "13:00", "daisy");

        // when
        // then
        assertThatThrownBy(() -> reservationService.make(request))
            .isInstanceOf(ReservationException.class)
            .hasMessageContaining("이미 예약되었습니다.");
    }

    @DisplayName("예약 목록을 조회한다.")
    @Test
    void check() {
        // given
        ReservationRequest request = new ReservationRequest("2022-08-11", "14:00", "dani");
        reservationService.make(request);

        List<ReservationResponse> expected = List.of(
            new ReservationResponse(null, "2022-08-11", "13:00", "dani"),
            new ReservationResponse(null, "2022-08-11", "14:00", "dani")
        );

        // when
        List<ReservationResponse> responses = reservationService.check("2022-08-11");

        // then
        assertThat(responses)
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @DisplayName("예약을 취소한다.")
    @Test
    void cancel() {
        // given
        // when
        reservationService.cancel("2022-08-11", "13:00");

        // then
        assertThat(reservationService.check("2022-08-11")).isEmpty();
    }
}
