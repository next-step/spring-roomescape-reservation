package roomescape.domain.reservation.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.domain.reservation.domain.*;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.support.IntegrationTestSupport;

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

    @DisplayName("예약과 테마를 같이 조회한다.")
    @Test
    void fetchReservationThemes() {
        // given
        final ReservationTime time1 = saveReservationTime(LocalTime.of(12, 0));
        final Theme theme1 = saveTheme("name1", "description1", "thumbnail1");
        final Reservation reservation1 = Reservation.builder()
                .themeId(theme1.getId())
                .name(new ReservationGuestName("reservation1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 8)))
                .timeId(time1.getId())
                .status(ReservationStatus.CONFIRMED)
                .reservedAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation reservationSaved1 = reservationRepository.save(reservation1);

        final ReservationTime time2 = saveReservationTime(LocalTime.of(14, 0));
        final Theme theme2 = saveTheme("name2", "description2", "thumbnail2");
        final Reservation reservation2 = Reservation.builder()
                .themeId(theme2.getId())
                .name(new ReservationGuestName("reservation2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 10)))
                .timeId(time2.getId())
                .status(ReservationStatus.CONFIRMED)
                .reservedAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation reservationSaved2 = reservationRepository.save(reservation2);

        // when
        final List<ReservationTimeThemeDto> actual = sut.fetchReservationThemes();

        // then
        assertThat(actual).hasSize(2)
                .extracting("reservation", "time", "theme")
                .containsExactlyInAnyOrder(
                        tuple(reservationSaved1, time1, theme1),
                        tuple(reservationSaved2, time2, theme2)
                );
    }

    private ReservationTime saveReservationTime(final LocalTime startAt) {
        final ReservationTime time = ReservationTime.builder()
                .startAt(startAt)
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        return timeRepository.save(time);
    }

    private Theme saveTheme(String name, String description, String thumbnail) {
        final Theme theme = Theme.builder()
                .name(name)
                .description(description)
                .thumbnail(thumbnail)
                .deleted(false)
                .build();
        return themeRepository.save(theme);
    }
}
