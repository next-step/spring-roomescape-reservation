package roomescape.core.domain.reservation;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.common.ClockHolder;
import roomescape.core.domain.mock.FakeClockHolder;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;

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
                        .startAt(LocalTime.of(12, 0))
                        .startAt(LocalTime.of(12, 0))
                        .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                        .build(),
                Theme.builder()
                        .id(new ThemeId(1000L))
                        .name("theme-name")
                        .description("theme-description")
                        .thumbnail("theme-thumbnail")
                        .activeStatus(ActiveStatus.ACTIVE)
                        .build(),
                clockHolder
        );

        assertAll(
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0))
        );
    }

    @Test
    void delete() {
        // given
        final Reservation sut = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(ReservationTime.builder()
                        .startAt(LocalTime.of(12, 0))
                        .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                        .build())
                .themeId(new ThemeId(1000L))
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();

        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        // when
        final Reservation actual = sut.delete(clockHolder);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.DELETED),
                () -> assertThat(actual.getDeletedAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }
}
