package roomescape.domain.reservation.service;

import java.time.LocalDateTime;

public class SystemClockHolder implements ClockHolder {

    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
