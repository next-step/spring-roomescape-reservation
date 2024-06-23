package roomescape.domain.reservationtime.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTimeCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationTimeCommandService sut;

    @Autowired
    ReservationTimeRepository timeRepository;

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

    @DisplayName("예약 시간 추가 시 기존 시간이 있다면 예외 발생")
    @Test
    void append_exception() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        timeRepository.save(time);

        final ReservationTimeAppendRequest request = new ReservationTimeAppendRequest(LocalTime.of(12, 0));

        // when
        assertThatThrownBy(() -> sut.append(request))
                .isInstanceOf(DupliactedReservationTimeException.class);
    }
}