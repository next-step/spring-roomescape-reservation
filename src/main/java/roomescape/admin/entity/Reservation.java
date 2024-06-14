package roomescape.admin.entity;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime reservationTime;

    private Theme theme;

    public static Reservation of(Long id, String name, String date, ReservationTime reservationTime, Theme theme){
        return new Reservation(id, name, date, reservationTime, theme);
    }

    private Reservation(Long id, String name, String date, ReservationTime reservationTime, Theme theme){
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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
        return reservationTime;
    }

    public Theme getTheme() {
        return theme;
    }
}