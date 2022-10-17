package nextsetp.repository.reservation;

import nextsetp.domain.reservation.InmemoryReservationRepository;
import nextsetp.domain.reservation.Reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


class InmemoryReservationRepositoryTest {
    private InmemoryReservationRepository inmemoryReservationRepository;

    @BeforeEach
    public void setUp() {
        inmemoryReservationRepository = new InmemoryReservationRepository();
    }

    @DisplayName("예약을 생성한다.")
    @Test
    void save() {
        Long id = inmemoryReservationRepository.save(new Reservation(LocalDate.now(), LocalTime.now(), "test"));
        Assertions.assertThat(id).isEqualTo(1L);
    }

    @DisplayName("해당 날짜의 예약을 조회한다.")
    @Test()
    void findAllBy() {
        String testDate = "2022-08-11";
        inmemoryReservationRepository.save(new Reservation(LocalDate.parse(testDate), LocalTime.now(), "test"));
        List<Reservation> reservations = inmemoryReservationRepository.findAllBy(testDate);
        Assertions.assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("해당 날짜와 시간의 예약을 삭제한다.")
    void delete() {
        String testDate = "2022-08-11";
        String testTime1 = "13:00";
        inmemoryReservationRepository.save(new Reservation(LocalDate.parse(testDate), LocalTime.parse(testTime1), "test"));

        String testTime2 = "14:00";
        inmemoryReservationRepository.save(new Reservation(LocalDate.parse(testDate), LocalTime.parse(testTime2), "test"));

        inmemoryReservationRepository.delete(testDate, testTime1);
        Assertions.assertThat(inmemoryReservationRepository.findAllBy(testDate).size()).isEqualTo(1);
    }
}