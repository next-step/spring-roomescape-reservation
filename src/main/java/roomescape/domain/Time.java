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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Time time)) {
            return false;
        }

        if (id != time.id) {
            return false;
        }
        return startAt.equals(time.startAt);
    }

    @Override
    public int hashCode() {
        int result = startAt.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public String getStartAt() {
        return startAt;
    }

    public long getId() {
        return id;
    }
}
