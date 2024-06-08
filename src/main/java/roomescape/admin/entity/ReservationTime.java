package roomescape.admin.entity;

public class ReservationTime {
    private long id;
    private String startAt;

    public static ReservationTime of(Long id, String startAt){
        return new ReservationTime(id, startAt);
    }

    private ReservationTime(Long id, String startAt){
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}