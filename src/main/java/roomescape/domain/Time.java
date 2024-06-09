package roomescape.domain;

public class Time {

    private final String startAt;
    private long id;

    private Time(long id, String startAt) {
        this.startAt = startAt;
        this.id = id;
    }

    private Time(String startAt) {
        this.startAt = startAt;
    }

    public static Time createReservationTime(String startAt) {
        return new Time(startAt);
    }

    public static Time createReservationTime(long id, String startAt) {
        return new Time(id, startAt);
    }

    public String getStartAt() {
        return startAt;
    }

    public long getId() {
        return id;
    }
}
