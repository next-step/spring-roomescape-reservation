package nextstep.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("날짜와 시간, 이름을 넣어 Reservation 을 저장한다.")
    void save01() {
        ReservationRequest request = createRequest(LocalDate.parse("2022-08-10"));
        long id = reservationService
            .save(request.getDate(), request.getTime(), request.getName());

        assertThat(id).isNotZero();
    }

    @Test
    @DisplayName("중복된 날짜와 시간을 넣어 Reservation 을 저장할 수 없다.")
    void save02() {
        ReservationRequest request = createRequest(LocalDate.parse("2022-08-11"));
        reservationService.save(request.getDate(), request.getTime(), request.getName());

        assertThatThrownBy(
            () -> reservationService.save(request.getDate(), request.getTime(), request.getName())
        ).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("날짜를 이용해 Reservations 를 조회한다.")
    void findReservationsByDate() {
        LocalDate date = LocalDate.parse("2022-08-12");
        ReservationRequest request = createRequest(date);
        long id = reservationService
            .save(request.getDate(), request.getTime(), request.getName());
        final Reservation expected = new Reservation(id, request.getDate(), request.getTime(),
            request.getName());

        List<Reservation> reservations = reservationService
            .findReservationsByDate(date);

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0)).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("날짜와 시간을 넣어 Reservation 을 삭제한다.")
    void deleteByLocalDateAndLocalTime() {
        LocalDate date = LocalDate.parse("2022-08-12");
        ReservationRequest request = createRequest(date);
        reservationService.save(request.getDate(), request.getTime(), request.getName());

        assertThat(reservationService.findReservationsByDate(date)).hasSize(1);
        reservationService.deleteByLocalDateAndLocalTime(date, LocalTime.parse("13:00"));
        assertThat(reservationService.findReservationsByDate(date)).isEmpty();
    }

    private ReservationRequest createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";
        return new ReservationRequest(date, time, name);
    }
}
