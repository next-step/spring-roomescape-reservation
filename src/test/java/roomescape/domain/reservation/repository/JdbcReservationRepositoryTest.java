package roomescape.domain.reservation.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDateTime;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JdbcReservationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    JdbcReservationRepository sut;

    @Test
    void save() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(reservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @DisplayName("저장 시, id가 있는 경우 모든 컬럼 update")
    @Test
    void save_exists_id() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        final Reservation newReservation = Reservation.builder()
                .id(saved.getId())
                .name(new ReservationGuestName("new-name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2025, 6, 8, 12, 0)))
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(newReservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("new-name")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2025, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 6, 4, 12, 0))
        );
    }

    @Test
    void findById() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        // when
        final Optional<Reservation> actualOpt = sut.findById(saved.getId());

        assertThat(actualOpt).isNotEmpty();
        final Reservation actual = actualOpt.get();

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @Test
    void findById_empty() {
        final Optional<Reservation> actual = sut.findById(-3000L);
        assertThat(actual).isEmpty();
    }

    @Test
    void findByNameAndDateTime() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        // when
        final Optional<Reservation> actualOpt = sut.findByNameAndDateTime(
                new ReservationGuestName("name"),
                new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))
        );

        // then
        assertThat(actualOpt).isNotEmpty();

        final Reservation actual = actualOpt.get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDateTime()).isEqualTo(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0))),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @Test
    void findAll() {
        // given
        final Reservation r1 = Reservation.builder()
                .name(new ReservationGuestName("r1"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(r1);

        final Reservation r2 = Reservation.builder()
                .name(new ReservationGuestName("r2"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2025, 6, 8, 12, 0)))
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();
        sut.save(r2);

        // when
        final List<Reservation> actual = sut.findAll();

        // then
        assertThat(actual).hasSize(2)
                .extracting("name")
                .containsExactly(
                        new ReservationGuestName("r1"),
                        new ReservationGuestName("r2")
                );
    }

    @Test
    void deleteAllInBatch() {
        // given
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .dateTime(new ReservationDateTime(LocalDateTime.of(2024, 6, 8, 12, 0)))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(reservation);

        sut.deleteAllInBatch();
    }
}