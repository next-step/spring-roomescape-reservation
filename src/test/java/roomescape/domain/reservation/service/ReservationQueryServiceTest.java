package roomescape.domain.reservation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservation.service.response.ReservationQueryResponse;
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
    ReservationRepository repository;

    @DisplayName("예약 전체 조회 시 확정된 예약만 조회된다.")
    @Test
    void fetchAll() {
        // given
        final Reservation confirmed = Reservation.builder()
                .name(new ReservationGuestName("confirmed"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        repository.save(confirmed);

        final Reservation canceled = Reservation.builder()
                .name(new ReservationGuestName("canceled"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 3, 8, 11, 0)))
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2023, 6, 4, 12, 0))
                .build();
        repository.save(canceled);

        // when
        final List<ReservationQueryResponse> actual = sut.findAll();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "date", "time")
                .containsExactly(
                        tuple("confirmed", LocalDate.of(2024, 6, 8), LocalTime.of(12, 0))
                );
    }
}