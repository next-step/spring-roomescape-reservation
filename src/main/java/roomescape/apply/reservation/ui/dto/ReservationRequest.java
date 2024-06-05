package roomescape.apply.reservation.ui.dto;

import org.springframework.util.StringUtils;
import roomescape.support.ReservationDateChecker;

public record ReservationRequest(
        String name,
        String date,
        long timeId
) {

    public ReservationRequest {
        validateValues(name, date);
    }

    private static void validateValues(String name, String date) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(date)) {
            String message = String.format("필수 값은 비어 있을 수 없습니다. name = %s, date = %s", name, date);
            throw new IllegalArgumentException(message);
        }

        if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalArgumentException("Date must be in the format yyyy-MM-dd");
        }

        if (ReservationDateChecker.isAvailableDateTime(date)) {
            throw new IllegalArgumentException("이미 종료된 예약입니다");
        }
    }
}
