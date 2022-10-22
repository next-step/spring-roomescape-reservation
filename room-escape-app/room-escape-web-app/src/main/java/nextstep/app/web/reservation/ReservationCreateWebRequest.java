package nextstep.app.web.reservation;

import nextstep.core.reservation.in.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;

class ReservationCreateWebRequest {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private Long scheduleId;

    private ReservationCreateWebRequest() {
    }

    public ReservationCreateRequest to() {
        return new ReservationCreateRequest(date, time, name, scheduleId);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
