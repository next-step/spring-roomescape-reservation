package roomescape.domain;

public class Reservation {

    private long id;
    private String name;
    private String date;
    private String time;

    private Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private Reservation(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation createReservation(long id, String name, String date, String time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createReservationWithoutId(String name, String date, String time) {
        return new Reservation(name, date, time);
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
}
