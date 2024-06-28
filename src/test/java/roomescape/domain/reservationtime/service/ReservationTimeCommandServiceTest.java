package roomescape.domain.reservationtime.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.exception.ReservationTimeAlreadyInUse;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
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

    @DisplayName("예약 시간을 추가할 수 있다")
    @Test
    void append() {
        // given
        final ReservationTimeAppendRequest request = new ReservationTimeAppendRequest(LocalTime.of(6, 0));

        // when
        final ReservationTime actual = sut.append(request);

        // then
        assertAll(
                () -> assertThat(actual.getId().getValue()).isNotNull(),
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
        final ReservationTime savedTime = timeRepository.save(time);

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation savedReservation = reservationRepository.save(reservation);

        // when & then
        assertThatThrownBy(() -> sut.delete(savedTime.getId()))
                .isInstanceOf(ReservationTimeAlreadyInUse.class)
                .hasMessage(
                        "Cannot delete ReservationTime(id=%d). It's already in use by Reservation(id=%s)"
                                .formatted(savedTime.getIdValue(), savedReservation.getId())
                );
    }

    @DisplayName("예약 시간 삭제 시 해당 시간에 예약된 예약이 취소 상태면 예외 발생하지 않음")
    @Test
    void delete_canceled() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        reservationRepository.save(reservation);

        // when & then
        Assertions.assertDoesNotThrow(() -> sut.delete(savedTime.getId()));

        final List<ReservationTime> actual = timeRepository.findAll();
        assertThat(actual).hasSize(0);
    }
}