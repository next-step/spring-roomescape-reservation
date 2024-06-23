package roomescape.domain.reservation.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.request.ReserveRequest;
import roomescape.domain.reservation.service.response.ReserveResponse;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
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
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimeRepository timeRepository;

    @DisplayName("예약 정보로 예약을 생성한다.")
    @Test
    void reserve() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 8))
                .timeId(savedTime.getIdValue())
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

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        final Reservation saved = reservationRepository.save(reservation);
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));

        final ReservationCommandService sut = new ReservationCommandService(
                reservationRepository,
                timeRepository,
                clockHolder
        );

        // when
        sut.cancel(new ReservationId(saved.getId()));

        // then
        assertThat(reservationRepository.findAll()).hasSize(1);

        final Reservation actual = reservationRepository.findById(saved.getId()).get();
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
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

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 23))
                .timeId(savedTime.getIdValue())
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

        final Reservation reservation = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        reservationRepository.save(reservation);

        final ReserveRequest request = ReserveRequest.builder()
                .name("brie")
                .date(LocalDate.of(2024, 6, 23))
                .timeId(savedTime.getIdValue())
                .build();

        // when & then
        Assertions.assertDoesNotThrow(() -> sut.reserve(request));
    }
}