package roomescape.apply.reservationtime.ui.dto;

import org.springframework.util.StringUtils;

public record ReservationTimeRequest(
        String startAt
) {

    public ReservationTimeRequest {
        validateValues(startAt);
    }

    private static void validateValues(String startAt) {
        if (!StringUtils.hasText(startAt)) {
            String message = String.format("필수 값은 비어 있을 수 없습니다. startAt = %s", startAt);
            throw new IllegalArgumentException(message);
        }
        if (!startAt.matches("^\\d{2}:\\d{2}$")) {
            throw new IllegalArgumentException("Time must be in the format HH:mm");
        }
    }
}
