package roomescape.model;

public class Reservation {
    private Long id;
    private String date;
    private String name;

    private Long timeId;
    private ReservationTime time;

    public Reservation() {
    }

    public Reservation(Long id, String date, String name, Long timeId, ReservationTime time) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.timeId = timeId;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }

}
