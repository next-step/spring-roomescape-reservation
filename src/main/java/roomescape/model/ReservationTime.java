package roomescape.model;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(String startAt) {
        this(0L, startAt);
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

}
