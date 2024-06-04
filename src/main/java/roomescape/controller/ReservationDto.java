package roomescape.controller;

import java.util.concurrent.atomic.AtomicLong;

public class ReservationDto {
    private Long id;
    private String name;
    private String date;
    private String time;

    public ReservationDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Reservation toEntity() {
        final AtomicLong atomicLong = new AtomicLong();
        return new Reservation(atomicLong.incrementAndGet(), name, date, time);
    }
}
