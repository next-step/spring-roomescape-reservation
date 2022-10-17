package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {

    private Long id;

    private final Long scheduleId;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(Long id, Long scheduleId, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static Reservation of(Long scheduleId, LocalDate date, LocalTime time, String name) {
        return new Reservation(0L, scheduleId, date, time, name);
    }

    public boolean isSameScheduleIdAndDateTime(Long scheduleId, LocalDate date, LocalTime time) {
        return Objects.equals(this.scheduleId, scheduleId) && Objects.equals(this.date, date) && Objects.equals(this.time, time);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }
}
