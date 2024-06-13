package roomescape.reservation;

import roomescape.time.Time;
import roomescape.time.TimeEntity;

public class Reservation {
    private Long id;
    private String name;
    private final ReservationDate date;
    private Time time;

    public static Reservation createdByEntity(ReservationEntity reservationEntity, TimeEntity time) {
        return new Reservation(reservationEntity.getId(),
                reservationEntity.getName(),
                new ReservationDate(reservationEntity.getDate()),
                Time.from(time));
    }

    public Reservation(Long id, String name, String date, Long timeId, String startAt) {
        this(id, name, new ReservationDate(date), new Time(timeId, startAt));
    }

    public Reservation(Long id, String name, ReservationDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public ReservationDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }
}
