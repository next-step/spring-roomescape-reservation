package roomescape.domain.theme.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationDate;
import roomescape.domain.reservation.domain.ReservationGuestName;
import roomescape.domain.reservation.domain.ReservationRepository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.theme.application.request.ThemeAppendRequest;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.domain.theme.exception.ThemeAlreadyInUseException;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ThemeServiceTest extends IntegrationTestSupport {

    @Autowired
    ThemeService sut;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ThemeRepository themeRepository;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Autowired
    ClockHolder clockHolder;

    @DisplayName("테마를 추가할 수 있다")
    @Test
    void appendTheme() {
        // given
        final ThemeAppendRequest request = ThemeAppendRequest.builder()
                .name("name")
                .description("description")
                .thumbnail("https://thumbnail.com")
                .build();

        // when
        final Theme actual = sut.appendTheme(request);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("name"),
                () -> assertThat(actual.getDescription()).isEqualTo("description"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com"),
                () -> assertThat(actual.isDeleted()).isFalse()
        );
    }

    @DisplayName("id로 테마를 삭제할 수 있다")
    @Test
    void deleteTheme() {
        // given
        final Theme themeToDelete = saveTheme("name1", "description1", "https://thumbnail.com1");
        final ThemeId themeId = themeToDelete.getId();

        // when
        sut.deleteTheme(themeId);

        // then
        final Theme actual = themeRepository.findById(themeId).get();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo("name1"),
                () -> assertThat(actual.getDescription()).isEqualTo("description1"),
                () -> assertThat(actual.getThumbnail()).isEqualTo("https://thumbnail.com1"),
                () -> assertThat(actual.isDeleted()).isTrue()
        );
    }

    @DisplayName("테마 삭제 시 해당 테마로 예약된 예약이 있으면 예외가 발생한다")
    @Test
    void delete_theme_failed() {
        // given
        final Theme theme = saveTheme("name", "description", "thumbnail");
        final ReservationTime time = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));
        final Reservation reservation = Reservation.defaultOf(
                new ReservationGuestName("name"),
                new ReservationDate(LocalDate.of(2024, 10, 3)),
                time,
                theme,
                clockHolder
        );
        reservationRepository.save(reservation);

        // when
        assertThatThrownBy(() -> sut.deleteTheme(theme.getId()))
                .isInstanceOf(ThemeAlreadyInUseException.class);

        assertThat(themeRepository.findById(theme.getId())).isNotEmpty();
    }

    private ReservationTime saveTime(final LocalTime startAt, final LocalDateTime createdAt) {
        final ReservationTime time = ReservationTime.builder()
                .startAt(startAt)
                .createdAt(createdAt)
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
