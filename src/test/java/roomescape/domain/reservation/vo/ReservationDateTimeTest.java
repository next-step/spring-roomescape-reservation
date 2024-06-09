package roomescape.domain.reservation.vo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class ReservationDateTimeTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm";

    @Test
    void fetchReservationDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 6, 19, 25);
        ReservationDateTime reservationDateTime = new ReservationDateTime(localDateTime);

        String dateActual = reservationDateTime.fetchReservationDateTime(DATE_FORMAT);
        String timeActual = reservationDateTime.fetchReservationDateTime(TIME_FORMAT);

        assertSoftly(softly -> {
                    softly.assertThat(dateActual).isEqualTo("2024-06-06");
                    softly.assertThat(timeActual).isEqualTo("19:25");
                }
        );
    }
}