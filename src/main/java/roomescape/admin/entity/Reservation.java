package roomescape.admin.entity;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime reservationTime;

    public static Reservation of(Long id, String name, String date, ReservationTime reservationTime){
        return new Reservation(id, name, date, reservationTime);
    }

    private Reservation(Long id, String name, String date, ReservationTime reservationTime){
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
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
}