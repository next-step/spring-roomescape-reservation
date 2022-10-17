package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDeleteRequest {
    private final Long scheduleId;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationDeleteRequest(Long scheduleId, LocalDate date, LocalTime time) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
    }

    public static ReservationDeleteRequest of(Long scheduleId, String date, String time) {
        return new ReservationDeleteRequest(scheduleId, LocalDate.parse(date), LocalTime.parse(time + ":00"));
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
