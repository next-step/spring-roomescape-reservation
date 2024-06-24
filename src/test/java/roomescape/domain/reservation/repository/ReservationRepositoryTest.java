package roomescape.domain.reservation.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.model.ReservationDate;
import roomescape.domain.reservation.model.ReservationGuestName;
import roomescape.domain.reservation.model.ReservationStatus;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    ReservationRepository sut;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Test
    void save() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(reservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @DisplayName("저장 시, id가 있는 경우 모든 컬럼 update")
    @Test
    void save_exists_id() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        final Reservation newReservation = Reservation.builder()
                .id(saved.getId())
                .name(new ReservationGuestName("new-name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CANCELED)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(newReservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("new-name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CANCELED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 6, 4, 12, 0))
        );
    }

    @Test
    void findById() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
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
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
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
    void findByNameDateTime() {
        // given
        final ReservationTime time = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));
        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(time)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        // when
        final Optional<Reservation> actualOpt = sut.findBy(
                new ReservationGuestName("name"),
                new ReservationDate(LocalDate.of(2024, 6, 23)),
                time.getId()
        );

        // then
        assertThat(actualOpt).isPresent();
        final Reservation actual = actualOpt.get();

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getStatus()).isEqualTo(ReservationStatus.CONFIRMED),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    private ReservationTime saveTime(LocalTime startAt, LocalDateTime createdAt) {
        final ReservationTime time = ReservationTime.builder()
                .startAt(startAt)
                .createdAt(createdAt)
                .build();
        return timeRepository.save(time);
    }

    @Test
    void findAll() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation r1 = Reservation.builder()
                .name(new ReservationGuestName("r1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(r1);

        final Reservation r2 = Reservation.builder()
                .name(new ReservationGuestName("r2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
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
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        sut.deleteAllInBatch();

        final List<Reservation> actual = sut.findAll();
        assertThat(actual).hasSize(0);
    }

    @Test
    void findByTimeId() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .name(new ReservationGuestName("name1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        final Reservation reservation2 = Reservation.builder()
                .name(new ReservationGuestName("name2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation2);

        // when
        final List<Reservation> actual = sut.findAllByTimeId(savedTime.getId());

        // then
        assertThat(actual).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        new ReservationGuestName("name1"),
                        new ReservationGuestName("name2")
                );
    }
}