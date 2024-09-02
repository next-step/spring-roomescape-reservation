package roomescape.db.core.reservation;

import org.junit.jupiter.api.Test;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.theme.ThemeId;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationEntityTest {

    @Test
    void fromModel() {
        // given
        final Reservation reservation = Reservation.builder()
                .id(10L)
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final ReservationEntity actual = ReservationEntity.fromModel(reservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(10L),
                () -> assertThat(actual.getThemeId()).isEqualTo(100L),
                () -> assertThat(actual.getTimeId()).isEqualTo(1000L),
                () -> assertThat(actual.getName()).isEqualTo("name"),
                () -> assertThat(actual.getDate()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @Test
    void toModel() {
        // given
        final ReservationEntity sut = ReservationEntity.builder()
                .id(10L)
                .themeId(100L)
                .timeId(1000L)
                .name("name")
                .date(LocalDate.of(2024, 6, 23))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.toModel();

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(10L),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(100L)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(1000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }
}
