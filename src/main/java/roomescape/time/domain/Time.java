package roomescape.time.domain;

import roomescape.time.error.exception.ErrorCode;
import roomescape.time.error.exception.TimeException;

public class Time {

    private static final String REGEX_FORMAT = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    private Long id;
    private String startAt;

    public Time(Long id, String startAt) {
        this.id = id;
        checkFormats(startAt);
        this.startAt = startAt;
    }

    private void checkFormats(String startAt) {
        if (!startAt.matches(REGEX_FORMAT)) {
            throw new TimeException(ErrorCode.INVALID_TIME_FORMAT_ERROR);
        }
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
