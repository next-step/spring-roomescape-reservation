package roomescape.domain.reservation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
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

    @DisplayName("예약 전체 조회 시 확정된 예약만 조회된다.")
    @Test
    void fetchAll() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime savedTime = timeRepository.save(time);

        final Reservation confirmed = Reservation.builder()
                .name(new ReservationGuestName("confirmed"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 8)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        reservationRepository.save(confirmed);

        final Reservation canceled = Reservation.builder()
                .name(new ReservationGuestName("canceled"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2023, 6, 4, 12, 0))
                .build();
        reservationRepository.save(canceled);

        // when
        final List<Reservation> actual = sut.fetchActiveReservations();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "date", "time")
                .containsExactly(
                        tuple(
                                new ReservationGuestName("confirmed"),
                                new ReservationDate(LocalDate.of(2024, 6, 8)),
                                savedTime
                        )
                );
    }
}