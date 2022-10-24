package nextstep.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {MemoryReservationRepository.class})
class MemoryReservationRepositoryTest {

    @Autowired
    private MemoryReservationRepository memoryReservationRepository;

    @Test
    @DisplayName("날짜와 시간 이름을 넣어 Reservation 을 저장한다.")
    void save() {
        final LocalDate date = LocalDate.parse("2022-08-10");
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";

        final long save = memoryReservationRepository.save(date, time, name);

        assertThat(save).isNotZero();
    }

    @Test
    @DisplayName("날짜와 시간 이름을 넣어 Reservation 을 저장한다.")
    void findReservationsByDate() {
        final LocalDate date = LocalDate.parse("2022-08-11");
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";
        final long id = memoryReservationRepository.save(date, time, name);
        final Reservation expected = new Reservation(id, date, time, name);

        List<Reservation> reservations = memoryReservationRepository
            .findReservationsByDate(date);

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0)).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("날짜와 시간을 넣어 Reservation 을 삭제한다.")
    void deleteByLocalDateAndLocalTime() {
        final LocalDate date = LocalDate.parse("2022-08-12");
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";
        memoryReservationRepository.save(date, time, name);

        assertThat(memoryReservationRepository.findReservationsByDate(date)).hasSize(1);
        memoryReservationRepository.deleteByLocalDateAndLocalTime(date, time);
        assertThat(memoryReservationRepository.findReservationsByDate(date)).isEmpty();
    }
}
