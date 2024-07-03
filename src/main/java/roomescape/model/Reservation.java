package roomescape.model;

public class Reservation {
    private Long id;
    private String date;
    private String name;
    private ReservationTime time;

    private Theme theme;

    public Reservation() {
    }

    public Reservation(Long id, String date, String name, ReservationTime time, Theme theme) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
        this.theme = theme;
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

    public Theme getTheme() {
        return theme;
    }
}
