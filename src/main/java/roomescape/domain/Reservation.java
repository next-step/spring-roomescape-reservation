package roomescape.domain;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;
    private ReservationTheme theme;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, ReservationTime time, ReservationTheme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(String name, String date, ReservationTime time, ReservationTheme theme) {
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

    public ReservationTheme getTheme() {
        return theme;
    }

    public Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id
                , reservation.getName()
                , reservation.getDate()
                , reservation.getTime()
                , reservation.getTheme());
    }
}
