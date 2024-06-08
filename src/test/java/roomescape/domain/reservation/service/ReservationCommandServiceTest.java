package roomescape.domain.reservation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationCommandService sut;

    @DisplayName("예약 정보로 예약을 생성한다.")
    @Test
    void reserve() {
        // given
        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .time(LocalTime.of(12, 0))
                .build();

        // when
        final ReserveResponse actual = sut.reserve(request);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
                .extracting("name", "date", "time")
                .containsExactly(
                        "brie", LocalDate.of(2024, 6, 8), LocalTime.of(12, 0)
                );
    }

}