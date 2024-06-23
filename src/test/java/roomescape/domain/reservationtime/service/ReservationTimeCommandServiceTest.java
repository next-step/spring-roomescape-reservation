package roomescape.domain.reservationtime.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTimeCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationTimeCommandService sut;

    @DisplayName("예약 시간을 추가할 수 있다")
    @Test
    void append() {
        // given
        final ReservationTimeAppendRequest request = new ReservationTimeAppendRequest(LocalTime.of(6, 0));

        // when
        final ReservationTime actual = sut.append(request);

        // then
        assertAll(
                () -> assertThat(actual.getId().getValue()).isNotNull(),
                () -> assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(6, 0))
        );
    }
}