package roomescape.domain.reservation.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.dto.ReservationId;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationReaderTest extends IntegrationTestSupport {

    @Autowired
    ReservationReader sut;

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void getById() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .timeStamp(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .build();
        final Long reservationId = reservationRepository.save(reservation).getId();

        // when
        final Reservation actual = sut.getById(new ReservationId(reservationId));

        // then
        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getTimeStamp()).isEqualTo(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)))
        );
    }

}