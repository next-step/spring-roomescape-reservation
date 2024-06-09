package roomescape.domain;

public class Reservation {

    private final String name;
    private final String date;
    private final Time time;
    private long id;

    private Reservation(long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private Reservation(String name, String date, Time time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createReservation(long id, String name, String date, long timeId, String startAt) {
        return new Reservation(id, name, date, Time.createReservationTime(timeId, startAt));
    }

    public static Reservation createReservation(String name, String date, long timeId, String startAt) {
        return new Reservation(name, date, Time.createReservationTime(timeId, startAt));
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

    public Time getTime() {
        return time;
    }
}
