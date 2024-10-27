package roomescape.repository.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.common.ActiveStatus;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationGuestName;
import roomescape.domain.reservation.ReservationRepository;
import roomescape.domain.reservation.exception.ReservationNotFoundException;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeId;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.theme.ThemeId;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    ReservationRepository sut;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Test
    void save() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(reservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(2000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @DisplayName("저장 시, id가 있는 경우 모든 컬럼 update")
    @Test
    void save_exists_id() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation reservationSaved = sut.save(reservation);

        final Reservation newReservation = Reservation.builder()
                .id(reservationSaved.getId())
                .themeId(new ThemeId(200L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("new-name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(newReservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(reservationSaved.getId()),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(200L)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(2000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("new-name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 6, 4, 12, 0))
        );
    }

    @Test
    void findNotDeletedReservations() {
        // given
        final Reservation reservation1 = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("reservation1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation1);

        final Reservation reservation2 = Reservation.builder()
                .themeId(new ThemeId(200L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("reservation2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();
        sut.save(reservation2);

        // when
        final List<Reservation> actual = sut.findNotDeletedReservations();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "themeId", "timeId")
                .containsOnly(tuple(new ReservationGuestName("reservation1"), new ThemeId(100L), new ReservationTimeId(1000L)));
    }

    @Test
    void getById() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        // when
        final Reservation actual = sut.getById(saved.getId());

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(100L)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(1000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 6, 4, 12, 0))
        );
    }

    @Test
    void getById_throw_exception_for_invalid_id() {
        assertThatThrownBy(() -> sut.getById(-3000L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void findByNameDateTime() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        // when
        final Optional<Reservation> actualOpt = sut.findBy(
                new ReservationGuestName("name"),
                new ReservationDate(LocalDate.of(2024, 6, 23)),
                new ReservationTimeId(1000L)
        );

        // then
        assertThat(actualOpt).isPresent();
        final Reservation actual = actualOpt.get();

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(100L)),
                () -> assertThat(actual.getTimeId()).isEqualTo(new ReservationTimeId(1000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
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
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("r1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(r1);

        final Reservation r2 = Reservation.builder()
                .themeId(new ThemeId(200L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("r2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();
        sut.save(r2);

        // when
        final List<Reservation> actual = sut.findNotDeletedReservations();

        // then
        assertThat(actual).hasSize(2)
                .extracting("name", "themeId", "timeId")
                .containsExactly(
                        tuple(new ReservationGuestName("r1"), new ThemeId(100L), new ReservationTimeId(1000L)),
                        tuple(new ReservationGuestName("r2"), new ThemeId(200L), new ReservationTimeId(2000L))
                );
    }

    @Test
    void findAllByTimeId() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("r1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        final Reservation reservation2 = Reservation.builder()
                .themeId(new ThemeId(200L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("r2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation2);

        final ReservationTimeId timeId = new ReservationTimeId(1000L);

        // when
        final List<Reservation> actual = sut.findAllByTimeId(timeId);

        // then
        assertThat(actual).hasSize(2)
                .extracting("name", "themeId", "timeId")
                .containsExactly(
                        tuple(new ReservationGuestName("r1"), new ThemeId(100L), new ReservationTimeId(1000L)),
                        tuple(new ReservationGuestName("r2"), new ThemeId(200L), new ReservationTimeId(1000L))
                );
    }

    @Test
    void findAllByThemeId() {
        // given
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(100L))
                .timeId(new ReservationTimeId(1000L))
                .name(new ReservationGuestName("r1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        final Reservation reservation2 = Reservation.builder()
                .themeId(new ThemeId(200L))
                .timeId(new ReservationTimeId(2000L))
                .name(new ReservationGuestName("r2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation2);

        final ThemeId themeId = new ThemeId(100L);

        // when
        final List<Reservation> actual = sut.findAllByThemeId(themeId);

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "themeId", "timeId")
                .containsExactly(
                        tuple(new ReservationGuestName("r1"), new ThemeId(100L), new ReservationTimeId(1000L))
                );
    }
}
