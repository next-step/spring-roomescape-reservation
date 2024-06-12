package roomescape.reservationTime;

import java.util.Objects;

public class ReservationTime {

    private Long id;
    private String startAt;

    public ReservationTime(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public ReservationTime(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
