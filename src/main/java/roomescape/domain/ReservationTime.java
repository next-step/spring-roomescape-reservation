package roomescape.domain;

import roomescape.dto.ReservationTimeDto;

import java.time.LocalTime;

public class ReservationTime {

    private Long id;

    private LocalTime startAt;


    public static ReservationTime toEntity(ReservationTimeDto dto) {
        return new ReservationTime(dto.getId(), dto.getStartAt());
    }

    public ReservationTime(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = LocalTime.parse(startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "id=" + id +
                ", startAt=" + startAt +
                '}';
    }
}
