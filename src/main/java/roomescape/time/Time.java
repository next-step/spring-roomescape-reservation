package roomescape.time;

public class Time {
    private Long id;

    private final ReservationTime startAt;

    public static Time from(TimeEntity entity) {
        return new Time(entity.getId(), entity.getStartAt());
    }

    public Time(Long id, String startAt) {
        this.id = id;
        this.startAt = new ReservationTime(startAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationTime getStartAt() {
        return startAt;
    }

    public TimeEntity toEntity() {
        return new TimeEntity(id, startAt.toString());
    }
}
