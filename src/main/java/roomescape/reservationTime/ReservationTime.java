package roomescape.reservationTime;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class ReservationTime {

    private Long id;

    @NonNull
    private String startAt;

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public ReservationTime(Long id) {
        this.id = id;
    }

    public ReservationTime(@NonNull String startAt, ReservationTimePolicy reservationTimePolicy) {
        if (reservationTimePolicy.validateStartAt(startAt)) {
            throw new IllegalArgumentException("예약 시간 형식이 올바르지 않습니다.");
        }
        this.startAt = startAt;
    }

    public ReservationTime(Long id, @NonNull String startAt, ReservationTimePolicy reservationTimePolicy) {
        this.id = id;
        if (reservationTimePolicy.validateStartAt(startAt)) {
            throw new IllegalArgumentException("예약 시간 형식이 올바르지 않습니다.");
        }
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
