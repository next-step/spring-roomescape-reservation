package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ReservationRepositoryTest extends RepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        initReservationTable();
    }

    @DisplayName("예약 저장")
    @Test
    void save() {
        Reservation reservation = new Reservation(
            1L,
            LocalDate.of(2022, 10, 1),
            LocalTime.of(2, 0),
            "최현구"
        );

        Reservation saved = reservationRepository.save(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(reservation);
    }

    @DisplayName("날짜에 해당하는 모든 예약 조회")
    @Test
    void findAllByDate() {
        Reservation 예약_10월1일_12시 = saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(12, 0), "최현구"
        );
        Reservation 예약_10월1일_15시 = saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(15, 0), "최현구"
        );
        saveReservation(
            LocalDate.of(2022, 12, 9),
            LocalTime.of(12, 0), "최현구"
        );

        List<Reservation> reservations = reservationRepository.findAllByDate(LocalDate.of(
            2022,
            10,
            1
        ));

        assertThat(reservations).containsExactly(예약_10월1일_12시, 예약_10월1일_15시);
    }

    @DisplayName("날짜에 해당하는 모든 예약 조회할 때 저장되어 있는 예약이 없어도 빈 값이 조회된다.")
    @Test
    void findAllByDateEmpty() {
        List<Reservation> reservations = reservationRepository.findAllByDate(LocalDate.of(
            2022,
            10,
            1
        ));

        assertThat(reservations).isEmpty();
    }

    @DisplayName("날짜와 시간에 해당되는 예약 제거")
    @Test
    void deleteByDateTime() {
        saveReservation(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0), "최현구"
        );

        int count = reservationRepository.deleteByDateTime(
            LocalDate.of(2022, 10, 1),
            LocalTime.of(1, 0)
        );

        assertThat(count).isEqualTo(1);
        assertThat(reservationRepository.findAllByDate(LocalDate.of(2022, 10, 1))).isEmpty();
    }

    @DisplayName("스케줄 ID 를 통해 예약 유무를 파악한다.")
    @Test
    void existsByScheduleId() {
        saveReservation(
            1L,
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 1),
            "최현구"
        );

        boolean result = reservationRepository.existsByScheduleId(1L);

        assertThat(result).isTrue();
    }

    private Reservation saveReservation(LocalDate date, LocalTime time, String name) {
        return saveReservation(1L, date, time, name);
    }

    private Reservation saveReservation(
        Long scheduleId,
        LocalDate date,
        LocalTime time,
        String name
    ) {
        return reservationRepository.save(new Reservation(scheduleId, date, time, name));
    }
}
