package roomescape.db.core.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.core.domain.common.ActiveStatus;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationDate;
import roomescape.core.domain.reservation.ReservationGuestName;
import roomescape.core.domain.reservation.ReservationRepository;
import roomescape.core.domain.reservation.exception.ReservationNotFoundException;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;
import roomescape.core.domain.theme.ThemeId;
import roomescape.db.core.ApplicationContextTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationRepositoryTest extends ApplicationContextTest {

    @Autowired
    ReservationRepository sut;

    @Autowired
    ReservationTimeRepository timeRepository;

    @Test
    void save() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();

        // when
        final Reservation actual = sut.save(reservation);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
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
                .themeId(new ThemeId(1000L))
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        final Reservation newReservation = Reservation.builder()
                .id(saved.getId())
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("new-name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
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
                () -> assertThat(actual.getActiveStatus()).isEqualTo(ActiveStatus.ACTIVE),
                () -> assertThat(actual.getCreatedAt()).isEqualTo(LocalDateTime.of(2025, 6, 4, 12, 0))
        );
    }

    @Test
    void findNotDeletedReservations() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation1 = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("reservation1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation1);

        final Reservation reservation2 = Reservation.builder()
                .themeId(new ThemeId(2000L))
                .name(new ReservationGuestName("reservation2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.DELETED)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();
        sut.save(reservation2);

        // when
        final List<Reservation> actual = sut.findNotDeletedReservations();

        // then
        assertThat(actual).hasSize(1)
                .extracting("name", "themeId")
                .containsOnly(tuple(new ReservationGuestName("reservation1"), new ThemeId(1000L)));
    }

    @Test
    void getById() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        final Reservation saved = sut.save(reservation);

        // when
        final Reservation actual = sut.getById(saved.getId());

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getThemeId()).isEqualTo(new ThemeId(1000L)),
                () -> assertThat(actual.getName()).isEqualTo(new ReservationGuestName("name")),
                () -> assertThat(actual.getDate().getValue()).isEqualTo(LocalDate.of(2024, 6, 23)),
                () -> assertThat(actual.getTime().getStartAt()).isEqualTo(LocalTime.of(12, 0)),
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
        final ReservationTime time = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));
        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("name"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(time)
                .activeStatus(ActiveStatus.ACTIVE)
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
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("r1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(r1);

        final Reservation r2 = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("r2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2025, 6, 4, 12, 0))
                .build();
        sut.save(r2);

        // when
        final List<Reservation> actual = sut.findNotDeletedReservations();

        // then
        assertThat(actual).hasSize(2)
                .extracting("name")
                .containsExactly(
                        new ReservationGuestName("r1"),
                        new ReservationGuestName("r2")
                );
    }

    @Test
    void findByTimeId() {
        // given
        final ReservationTime savedTime = saveTime(LocalTime.of(12, 0), LocalDateTime.of(2024, 6, 23, 7, 0));

        final Reservation reservation = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("name1"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
                .createdAt(LocalDateTime.of(2024, 6, 4, 12, 0))
                .build();
        sut.save(reservation);

        final Reservation reservation2 = Reservation.builder()
                .themeId(new ThemeId(1000L))
                .name(new ReservationGuestName("name2"))
                .date(new ReservationDate(LocalDate.of(2024, 6, 23)))
                .time(savedTime)
                .activeStatus(ActiveStatus.ACTIVE)
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
