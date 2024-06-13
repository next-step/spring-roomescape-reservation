package roomescape.reservation;


public class ReservationEntity {
    private Long id;
    private String name;
    private String date;
    private Long timeId;

    public ReservationEntity(String name, String date, Long timeId) {
        this(null, name, date, timeId);
    }

    public ReservationEntity(Long id, String  name, String date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }
}
