package nextstep.core.reservation.in;

import nextstep.core.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private final LocalDate date;
    private final LocalTime time;
    private final String name;
    private final Long scheduleId;

    public ReservationCreateRequest(LocalDate date, LocalTime time, String name, Long scheduleId) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.scheduleId = scheduleId;
    }

    public Reservation to() {
        return new Reservation(scheduleId, date, time, name);
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
