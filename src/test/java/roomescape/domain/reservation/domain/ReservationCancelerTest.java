package roomescape.domain.reservation.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationStatus;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.global.infrastructure.ClockHolder;
import roomescape.mock.FakeClockHolder;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationCancelerTest extends IntegrationTestSupport {

    @Autowired
    ReservationReader reader;

    @Autowired
    ReservationRepository repository;

    @Test
    void cancel() {
        // given
        final Reservation reservation = Reservation.builder()
                .id(1L)
                .name(new ReservationGuestName("brie"))
                .timeStamp(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 3, 8, 12, 0))
                .build();
        final Long reservationId = repository.save(reservation);
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));
        final ReservationCanceler sut = new ReservationCanceler(clockHolder, reader, repository);

        // when
        sut.cancel(new ReservationId(reservationId));

        // then
        assertThat(repository.findAll()).hasSize(1);

        final Reservation actual = repository.findById(reservationId).get();
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(1L),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("brie")),
                () -> assertThat(actual.getTimeStamp()).isEqualTo(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCanceledAt()).isEqualTo(LocalDateTime.of(2024, 6, 7, 12, 0)),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 3, 8, 12, 0))
        );
    }

}