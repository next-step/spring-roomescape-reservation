package roomescape.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {
    private static final String TIME_PATTERN = "HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private final LocalTime time;

    public ReservationTime(String time) {
        this.time = LocalTime.parse(time);
    }

    public LocalTime getTime() {
        return time;
    }

    @JsonValue
    @Override
    public String toString() {
        return time.format(formatter);
    }
}
