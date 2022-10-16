package nextstep.domain;

import nextstep.exception.ClientException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationsTest {
    @Test
    void add_WhenDuplicatedDateTimeReservationIsRequested_ThrowClientException() {
        Reservation reservation = new Reservation(LocalDate.of(2022, 8, 11), LocalTime.of(13, 10), "example");
        Reservations.add(reservation);

        assertThrows(ClientException.class, () -> Reservations.add(reservation));
    }
}
