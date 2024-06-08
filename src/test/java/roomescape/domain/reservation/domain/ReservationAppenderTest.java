package roomescape.domain.reservation.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationStatus;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.global.infrastructure.ClockHolder;
import roomescape.mock.FakeClockHolder;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ReservationAppenderTest extends IntegrationTestSupport {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void append() {
        // given
        final ClockHolder clockHolder = new FakeClockHolder(LocalDateTime.of(2024, 6, 7, 12, 0));
        final ReservationAppender sut = new ReservationAppender(reservationRepository, clockHolder);

        final ReservationAppend append = ReservationAppend.builder()
                .name(new ReservationGuestName("name"))
                .timeStamp(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .build();

        // when
        final Long actual = sut.append(append);

        // then
        assertThat(actual).isNotNull();
        final List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(1)
                .extracting("name", "timeStamp", "status", "createdAt")
                .containsExactly(
                        tuple(
                                new ReservationGuestName("name"),
                                new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)),
                                ReservationStatus.CONFIRMED,
                                LocalDateTime.of(2024, 6, 7, 12, 0)
                        )
                );
    }

}