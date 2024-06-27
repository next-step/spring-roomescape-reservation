package roomescape.util.clockholder;

import java.time.LocalDateTime;

public class SystemClockHolder implements ClockHolder {

    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
