package roomescape.Entity;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTime time;
    private final Theme theme;


    public Reservation(Long id, String name, String date, ReservationTime time, Theme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
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

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }
}
