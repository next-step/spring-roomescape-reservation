package roomescape.domain.reservation.vo;

import roomescape.domain.validator.ObjectValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservationDateTime(LocalDateTime dateTime) {

    public ReservationDateTime {
        ObjectValidator.validateNotNull(dateTime);
    }

    public String getFormatted(String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
