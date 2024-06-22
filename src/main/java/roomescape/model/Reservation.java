package roomescape.model;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation() {}

    public Reservation(Long id, String name, String date, ReservationTime time) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setTime(ReservationTime time) {
        this.time = time;
    }
}
