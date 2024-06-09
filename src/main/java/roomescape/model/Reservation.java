package roomescape.model;

public class Reservation {
    private Long id;
    private String date;
    private String name;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String date, String name, ReservationTime time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
