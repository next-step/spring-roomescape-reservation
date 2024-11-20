package roomescape.support;

import roomescape.domain.common.ClockHolder;

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
