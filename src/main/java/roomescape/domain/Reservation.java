package roomescape.domain;

import roomescape.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;

    private ReservationTime time;

    public static Reservation toEntity(Long id,String name, String date, Long timeId, String startAt) {
        return new Reservation(id, name, LocalDate.parse(date), new ReservationTime(timeId, startAt));
    }

    public Reservation() {
    }

    //create 들어옴.
    public Reservation(String name, String date, ReservationTime reservationTime) {
        this.name = name;
        this.date = LocalDate.parse(date);
        this.time = reservationTime;
    }

    public Reservation(Long id, String name, LocalDate date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = reservationTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
