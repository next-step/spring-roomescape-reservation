package roomescape.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationDateChecker {

    private ReservationDateChecker() {
        throw new UnsupportedOperationException("인스턴스화 할 수 없는 클래스입니다.");
    }

    public static boolean isAvailableDateTime(String date) {
        LocalDate reservationTime = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return reservationTime.isBefore(LocalDate.now());
    }

}
