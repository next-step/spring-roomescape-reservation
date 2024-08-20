package roomescape.core.api.reservation.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.api.support.IntegrationTestSupport;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.core.domain.reservation.ReservationRepository;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ReservationQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationQueryService sut;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Autowired
    ThemeRepository themeRepository;

    @DisplayName("예약 전체 조회 시 활성화 상태인 예약만 조회된다.")
    @Test
    void fetchAll() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);

        final Theme savedTheme = saveTheme("theme-name", "theme-description", "theme-thumbnail");

        final Reservation confirmed = Reservation.builder()
                .themeId(savedTheme.getId())
                .name(new ReservationGuestName("confirmed"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 8)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        reservationRepository.save(confirmed);

        final Reservation canceled = Reservation.builder()
                .themeId(savedTheme.getId())
                .name(new ReservationGuestName("canceled"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2023, 6, 4, 12, 0))
                .build();
        reservationRepository.save(canceled);

        // when
        final List<Reservation> actual = sut.fetchActiveReservations();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "date", "time")
                .containsExactly(
                        tuple(
                                new ReservationGuestName("confirmed"),
                                new ReservationDate(LocalDate.of(2024, 6, 8)),
                                savedTime
                        )
                );
    }

    private Theme saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .activeStatus(ActiveStatus.ACTIVE)
                .build();
        return themeRepository.save(theme);
    }
}
