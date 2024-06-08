package roomescape.mock;

import org.junit.jupiter.api.Test;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalDateTime;

public class FakeClockHolder implements ClockHolder {

    private final LocalDateTime currentSeoulTime;

    public FakeClockHolder(final LocalDateTime currentSeoulTime) {
        this.currentSeoulTime = currentSeoulTime;
    }

    @Override
    public LocalDateTime getCurrentSeoulTime() {
        return this.currentSeoulTime;
    }

}
