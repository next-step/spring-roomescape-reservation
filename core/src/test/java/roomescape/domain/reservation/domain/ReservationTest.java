package roomescape.domain.reservation.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;
import roomescape.support.FakeClockHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTest {

    @DisplayName("""
            예약 생성 시 기본 상태는 확정이고,
            예약 생성 시간은 현재 서울 시간이다.""")
    @Test
    void default_status() {
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        Reservation actual = Reservation.defaultOf(
                new ReservationGuestName("name"),
                new ReservationDate(LocalDate.of(2024, 6, 23)),
                ReservationTime.builder()
                        .id(new ReservationTimeId(2000L))
                        .startAt(LocalTime.of(12, 0))
                        .startAt(LocalTime.of(12, 0))
                        .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                        .build(),
                Theme.builder()
                        .id(new ThemeId(1000L))
                        .name("theme-name")
                        .description("theme-description")
                        .thumbnail("theme-thumbnail")
                        .deleted(false)
                        .build(),
                clockHolder
        );

        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getReservedAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(2000L))
        );
    }

    @Test
    void delete() {
        // given
        final Reservation sut = Reservation.builder()
                .id(1L)
                .themeId(new ThemeId(1000L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .status(ReservationStatus.CONFIRMED)
                .reservedAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();

        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        // when
        final Reservation actual = sut.cancel(clockHolder);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(2000L)),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getReservedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }
}
