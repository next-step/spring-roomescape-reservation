package nextstep.domain.reservation.service;

import nextstep.domain.reservation.model.Reservation;
import nextstep.exception.ClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {
    @Autowired
    private ReservationService service;

    @DisplayName("이미 생성된 예약과 같은 날짜, 시간으로 예약을 생성할 경우 예외가 발생한다.")
    @Test
    void create() {
        // given
        Reservation reservation = new Reservation(null, LocalDate.of(2022, 8, 11), LocalTime.of(13, 10), "example");
        service.create(reservation);

        // when, then
        assertThrows(ClientException.class, () -> service.create(reservation));
    }
}
