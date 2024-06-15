package roomescape.domain.reservation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.domain.model.Reservation;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemoryReservationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    MemoryReservationRepository sut;

    @Test
    void save() {
        final Reservation reservation = createReservation("name1", LocalDateTime.of(2024, 6, 8, 12, 0));

        final Long actual = sut.save(reservation).getId();

        assertThat(actual).isNotNull();
        assertThat(actual).isNotEqualTo(0);
    }

    @Test
    void findAll() {
        sut.save(createReservation("name1", LocalDateTime.of(2024, 6, 8, 12, 0)));
        sut.save(createReservation("name2", LocalDateTime.of(2024, 6, 8, 12, 0)));

        final List<Reservation> actual = sut.findAll();

        assertThat(actual).hasSize(2);
    }

    @Test
    void findById_exist() {
        final Long reservationId = sut.save(createReservation("name", LocalDateTime.of(2024, 6, 8, 12, 0))).getId();

        final Optional<Reservation> actual = sut.findById(reservationId);

        assertThat(actual).isNotEmpty();

        final Reservation reservation = actual.get();
        assertAll(
                () -> assertThat(reservation.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(reservation.getTimeStamp()).isEqualTo(new ReservationTimeStamp(LocalDateTime.of(2024, 6, 8, 12, 0)))
        );
    }

    @Test
    void findById_empty() {
        final Optional<Reservation> actual = sut.findById(1L);
        assertThat(actual).isEmpty();
    }

    private Reservation createReservation(final String name, LocalDateTime timestamp) {
        return Reservation.builder()
                .name(new ReservationGuestName(name))
                .timeStamp(new ReservationTimeStamp(timestamp))
                .build();
    }

}