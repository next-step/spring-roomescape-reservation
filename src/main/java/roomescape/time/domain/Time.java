package roomescape.time.domain;

import roomescape.time.global.error.exception.ErrorCode;
import roomescape.time.global.error.exception.InvalidValueException;

public class Time {

    private static final String REGEX_FORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    private Long id;
    private String startAt;

    public Time(Long id, String startAt) {
        this.id = id;
        checkStartAtFormat(startAt);
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    private void checkStartAtFormat(String startAt) {
        boolean result = startAt.matches(REGEX_FORMAT);
        if (!result) {
            throw new InvalidValueException(ErrorCode.INVALID_TIME_FORMAT_ERROR);
        }
    }
}
