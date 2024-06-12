package roomescape.reservation;

public class ReservationResponse {

    private Long id;

    private String name;

    private String date;

    private String time;

    private String themeName;

    public ReservationResponse(Long id, String name, String date, String time, String themeName) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.themeName = themeName;
    }

    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(),
            reservation.getName(),
            reservation.getDate().toString(),
            reservation.getReservationTime().getStartAt().toString(),
            reservation.getTheme().getName());
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

    public String getThemeName() {
        return themeName;
    }
}
