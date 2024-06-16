package roomescape.domain.reservation.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.global.infrastructure.ClockHolder;
import roomescape.mock.FakeClockHolder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTest {

    @DisplayName("""
            예약 생성 시 기본 상태는 확정이고,
            예약 생성 시간은 현재 서울 시간이다.""")
    @Test
    void default_status() {
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        Reservation sut = Reservation.defaultOf(
                new ReservationGuestName("name"),
                new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)),
                clockHolder
        );

        assertAll(
                () -> assertThat(sut.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(sut.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0))
        );
    }

    @Test
    void cancel() {
        // given
        final Reservation sut = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();

        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        // when
        final Reservation actual = sut.cancel(clockHolder);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }
}