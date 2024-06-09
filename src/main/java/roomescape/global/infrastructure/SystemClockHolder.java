package roomescape.global.infrastructure;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class SystemClockHolder implements ClockHolder {

    private static final ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");

    @Override
    public LocalDateTime getCurrentSeoulTime() {
        return ZonedDateTime.now(SEOUL_ZONE_ID).toLocalDateTime();
    }

}
