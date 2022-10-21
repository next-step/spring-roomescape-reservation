package nextstep.domain.reservation.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class Reservation {
    private final Long id;
    private final Long scheduleId;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    public Reservation(Long scheduleId, LocalDate date, LocalTime time, String name) {
        this(null, scheduleId, date, time, name);
    }

    public Reservation(Long id, Long scheduleId, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.name = name;
    }
}
