package roomescape.domain.reservationtime.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.model.ReservationTimeId;
import roomescape.support.IntegrationTestSupport;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTimeRepositoryTest extends IntegrationTestSupport {

    @Autowired
    ReservationTimeRepository sut;

    @Test
    void save() {
        // save1
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();

        final ReservationTime actual = sut.save(time);

        assertAll(
                () -> assertThat(actual.getId().getValue()).isNotNull(),
                () -> assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(12, 0))
        );

        // save2
        final ReservationTime time2 = ReservationTime.builder()
                .startAt(LocalTime.of(13, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();

        final ReservationTime actual2 = sut.save(time2);

        assertAll(
                () -> assertThat(actual2.getId().getValue()).isNotNull(),
                () -> assertThat(actual2.getId().getValue()).isNotEqualTo(actual.getId().getValue()),
                () -> assertThat(actual2.getStartAt()).isEqualTo(LocalTime.of(13, 0))
        );
    }

    @DisplayName("이미 id가 있을 경우 update 처리한다")
    @Test
    void save_update() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime saved = sut.save(time);

        final ReservationTime idExists = ReservationTime.builder()
                .id(saved.getId())
                .startAt(LocalTime.of(6, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();

        // when
        final ReservationTime actual = sut.save(idExists);
        assertAll(
                () -> assertThat(actual.getId().getValue()).isEqualTo(saved.getId().getValue()),
                () -> assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(6, 0))
        );
    }

    @Test
    void getById() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime saved = sut.save(time);

        // when
        final ReservationTime actual = sut.getById(saved.getId());

        // then
        assertAll(
                () -> assertThat(actual.getId().getValue()).isEqualTo(saved.getId().getValue()),
                () -> assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(12, 0))
        );
    }

    @Test
    void findAll() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(6, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        sut.save(time);

        final ReservationTime time2 = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        sut.save(time2);

        // when
        final List<ReservationTime> actual = sut.findAll();

        // then
        assertThat(actual).hasSize(2)
                .extracting("startAt")
                .containsExactly(
                        LocalTime.of(6, 0),
                        LocalTime.of(12, 0)
                );
    }

    @Test
    void deleteAllInBatch() {
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        sut.save(time);

        sut.deleteAllInBatch();

        List<ReservationTime> actual = sut.findAll();
        assertThat(actual).hasSize(0);
    }

    @Test
    void getByStartAt() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime saved = sut.save(time);

        // when
        final ReservationTime actual = sut.getByStartAt(LocalTime.of(12, 0));

        // then
        assertAll(
                () -> assertThat(actual.getId().getValue()).isEqualTo(saved.getId().getValue()),
                () -> assertThat(actual.getStartAt()).isEqualTo(LocalTime.of(12, 0))
        );
    }

    @Test
    void delete() {
        // given
        final ReservationTime time = ReservationTime.builder()
                .startAt(LocalTime.of(12, 0))
                .createdAt(LocalDateTime.of(2024, 6, 23, 7, 0))
                .build();
        final ReservationTime saved = sut.save(time);
        final ReservationTimeId timeId = saved.getId();

        // when
        sut.delete(timeId);

        // when
        final List<ReservationTime> actual = sut.findAll();
        assertThat(actual).hasSize(0);
    }
}