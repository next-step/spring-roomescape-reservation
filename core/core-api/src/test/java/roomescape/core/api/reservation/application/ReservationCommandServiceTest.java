package roomescape.core.api.reservation.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.api.mock.FakeClockHolder;
import roomescape.core.api.reservation.api.response.ReserveResponse;
import roomescape.core.api.reservation.application.request.ReserveRequest;
import roomescape.core.api.support.IntegrationTestSupport;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.common.ClockHolder;
import roomescape.core.domain.reservation.*;
import roomescape.core.domain.reservation.exception.DuplicatedReservationException;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .timeId(savedTime.getIdValue())
                .themeId(themeSaved.getId().value())
                .build();

        // when
        final ReserveResponse actual = sut.reserve(request);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual)
                .extracting("name", "date", "time")
                .containsExactly(
                        "brie", LocalDate.of(2024, 6, 8), LocalTime.of(12, 0)
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
        final ReservationTime savedTime = timeRepository.save(time);

        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .themeId(themeSaved.getId())
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
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
        assertThat(reservationRepository.findNotDeletedReservations()).hasSize(1);

        final Reservation actual = reservationRepository.getById(saved.getId());
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.DELETED),
                () -> assertThat(actual.getDeletedAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
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
        final ReservationTime savedTime = timeRepository.save(time);

        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .themeId(themeSaved.getId())
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 23))
                .timeId(savedTime.getIdValue())
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
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);
        final Theme themeSaved = saveTheme("theme-name", "theme-thumbnail", "theme-description");

        final Reservation reservation = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .themeId(themeSaved.getId())
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 23))
                .timeId(savedTime.getIdValue())
                .themeId(themeSaved.getId().value())
                .build();

        // when & then
        Assertions.assertDoesNotThrow(() -> sut.reserve(request));
    }

    private Theme saveTheme(String name, String thumbnail, String description) {
        final Theme theme = Theme.builder()
                .name(name)
                .thumbnail(thumbnail)
                .description(description)
                .activeStatus(ActiveStatus.ACTIVE)
                .build();

        return themeRepository.save(theme);
    }
}
