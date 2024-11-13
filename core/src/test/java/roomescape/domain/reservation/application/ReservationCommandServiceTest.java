package roomescape.domain.reservation.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.reservation.application.request.ReserveRequest;
import roomescape.domain.reservation.domain.*;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.reservationtime.exception.ReservationTimeOutOfRangeException;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.support.FakeClockHolder;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    ReservationCommandService sut;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Autowired
    ThemeRepository themeRepository;

    @DisplayName("예약 정보로 예약을 생성한다.")
    @Test
    void reserve() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 1))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime timeSaved = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 10, 4))
                .timeId(timeSaved.getIdValue())
                .themeId(themeSaved.getId().value())
                .build();

        final LocalDateTime currentSeoulTime = LocalDateTime.of(2024, 10, 4, 12, 0);
        final ReservationCommandService sut = new ReservationCommandService(
                reservationRepository,
                timeRepository,
                themeRepository,
                new FakeClockHolder(currentSeoulTime)
        );

        // when
        final ReservationId reservationId = sut.reserve(request);

        // then
        final Reservation reservation = reservationRepository.getById(reservationId.value());

        assertAll(
                () -> assertThat(reservation.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(reservation.getDate()).isEqualTo(new ReservationDate(LocalDate.of(2024, 10, 4))),
                () -> assertThat(reservation.getThemeId()).isEqualTo(themeSaved.getId()),
                () -> assertThat(reservation.getTimeId()).isEqualTo(timeSaved.getId())
        );
    }

    @DisplayName("예약 id로 예약을 취소한다")
    @Test
    void cancel() {
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

        final Reservation saved = reservationRepository.save(reservation);
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        final ReservationCommandService sut = new ReservationCommandService(
                reservationRepository,
                timeRepository,
                themeRepository,
                clockHolder
        );

        // when
        sut.cancel(new ReservationId(saved.getId()));

        // then
        final Reservation actual = reservationRepository.getById(saved.getId());
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTimeId()).isEqualTo(timeSaved.getId()),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getReservedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }

    @DisplayName("이미 같은 이름, 예약 날짜/시간으로 예약이 되어있는 경우 예약 시 예외 발생")
    @Test
    void reserve_exception() {
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

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 23))
                .timeId(timeSaved.getIdValue())
                .themeId(themeSaved.getId().value())
                .build();

        // when & then
        assertThatThrownBy(() -> sut.reserve(request))
                .isInstanceOf(DuplicatedReservationException.class);
    }

    @DisplayName("같은 이름 & 날짜/시간으로 예약이 존재하지만 비활성화 상태(취소)인 경우 예외 발생하지 않는다.")
    @Test
    void reserve_no_exception() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 1))
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

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 10, 24))
                .timeId(timeSaved.getIdValue())
                .themeId(themeSaved.getId().value())
                .build();

        final LocalDateTime currentSeoulTime = LocalDateTime.of(2024, 10, 4, 12, 0);
        final ReservationCommandService sut = new ReservationCommandService(
                reservationRepository,
                timeRepository,
                themeRepository,
                new FakeClockHolder(currentSeoulTime)
        );

        // when & then
        assertDoesNotThrow(() -> sut.reserve(request));
    }

    @DisplayName("현재 시간 이전의 날짜에 대해서는 예약을 생성 시 예외 발생")
    @Test
    void reserve_before_current_date_time() {
        // given
        final ReservationTime time = saveTime(LocalTime.of(11, 59), LocalDateTime.of(2024, 6, 23, 7, 0));
        final Theme theme = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 10, 4))
                .timeId(time.getId().value())
                .themeId(theme.getId().value())
                .build();

        final LocalDateTime currentSeoulTime = LocalDateTime.of(2024, 10, 4, 12, 0);
        final ReservationCommandService sut = new ReservationCommandService(
                reservationRepository,
                timeRepository,
                themeRepository,
                new FakeClockHolder(currentSeoulTime)
        );

        // when
        assertThatThrownBy(() -> sut.reserve(request))
                .isInstanceOf(ReservationTimeOutOfRangeException.class);
    }

    private ReservationTime saveTime(LocalTime startAt, LocalDateTime createdAt) {
        final ReservationTime time = ReservationTime.builder()
                .startAt(startAt)
                .createdAt(createdAt)
                .build();
        return timeRepository.save(time);
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
