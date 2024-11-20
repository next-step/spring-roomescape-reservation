package roomescape.domain.reservationtime.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.domain.*;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.exception.ReservationTimeAlreadyInUse;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTimeCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationTimeCommandService sut;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ThemeRepository themeRepository;

    @DisplayName("예약 시간을 추가할 수 있다")
    @Test
    void append() {
        // given
        final ReservationTimeAppendRequest request = new ReservationTimeAppendRequest(LocalTime.of(6, 0));

        // when
        final ReservationTime actual = sut.append(request);

        // then
        assertAll(
                () -> assertThat(actual.getId().value()).isNotNull(),
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

    @DisplayName("예약 시간을 id로 삭제할 수 있다.")
    @Test
    void delete_by_id() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime saved = timeRepository.save(time);

        // when
        sut.delete(saved.getId());

        // then
        final List<ReservationTime> actual = timeRepository.findAll();
        assertThat(actual).hasSize(0);
    }

    @DisplayName("예약 시간 삭제 시 해당 시간에 예약된 예약이 있으면 예외발생")
    @Test
    void delete() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime timeSaved = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .timeId(timeSaved.getId())
                .themeId(themeSaved.getId())
                .status(ReservationStatus.CONFIRMED)
                .reservedAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        // when & then
        assertThatThrownBy(() -> sut.delete(timeSaved.getId()))
                .isInstanceOf(ReservationTimeAlreadyInUse.class);
    }

    @DisplayName("예약 시간 삭제 시 해당 시간에 예약된 예약이 취소 상태면 예외 발생하지 않음")
    @Test
    void delete_canceled() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime timeSaved = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .timeId(timeSaved.getId())
                .themeId(themeSaved.getId())
                .status(ReservationStatus.CANCELED)
                .reservedAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        // when & then
        Assertions.assertDoesNotThrow(() -> sut.delete(timeSaved.getId()));

        final List<ReservationTime> actual = timeRepository.findAll();
        assertThat(actual).hasSize(0);
    }

    private Theme saveTheme(String name, String thumbnail, String description) {
        final Theme theme = Theme.builder()
                .name(name)
                .thumbnail(thumbnail)
                .description(description)
                .deleted(false)
                .build();

        return themeRepository.save(theme);
    }
}
