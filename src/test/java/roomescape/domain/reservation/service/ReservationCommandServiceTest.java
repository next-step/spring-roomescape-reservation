package roomescape.domain.reservation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;
import roomescape.global.infrastructure.ClockHolder;
import roomescape.mock.FakeClockHolder;
import roomescape.support.IntegrationTestSupport;

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
    ReservationRepository repository;

    @DisplayName("예약 정보로 예약을 생성한다.")
    @Test
    void reserve() {
        // given
        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .time(LocalTime.of(12, 0))
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
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        final Reservation saved = repository.save(reservation);
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        final ReservationCommandService sut = new ReservationCommandService(repository, clockHolder);

        // when
        sut.cancel(new ReservationId(saved.getId()));

        // then
        assertThat(repository.findAll()).hasSize(1);

        final Reservation actual = repository.findById(saved.getId()).get();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }

    @DisplayName("이미 같은 이름, 예약 날짜/시간으로 예약이 되어있는 경우 예약 시 예외 발생")
    @Test
    void reserve_exception() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        repository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .time(LocalTime.of(12, 0))
                .build();

        // when & then
        assertThatThrownBy(() -> sut.reserve(request))
                .isInstanceOf(DuplicatedReservationException.class);
    }

    @DisplayName("같은 이름 & 날짜/시간으로 예약이 존재하지만 비활성화 상태(취소)인 경우 예외 발생하지 않는다.")
    @Test
    void reserve_no_exception() {
        // given
        final Reservation reservation = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        repository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .time(LocalTime.of(12, 0))
                .build();

        // when & then
        Assertions.assertDoesNotThrow(() -> sut.reserve(request));
    }
}