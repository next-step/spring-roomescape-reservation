package roomescape;

public class Reservation {
    private Long id;
    private String date;
    private String name;
    private String time;

    public Reservation() {
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public Reservation(Long id, String date, String name, String time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Reservation(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.getDate(), reservation.getName(), reservation.getTime());
    }

}
