package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {

    private final long scheduleId;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public ReservationCreateRequest(long scheduleId, LocalDate date, LocalTime time, String name) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public long getScheduleId() {
        return scheduleId;
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

    public Reservation toObject() {
        return Reservation.of(this.scheduleId, this.date, this.time, this.name);
    }
}
