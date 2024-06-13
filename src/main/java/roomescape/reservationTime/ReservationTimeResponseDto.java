package roomescape.reservationTime;

public class ReservationTimeResponseDto {

    private Long id;
    private String startAt;

    public Long getId() { return id; }

    public String getStartAt() { return startAt; }

    public ReservationTimeResponseDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    @Override
    public String toString() {
        return "ReservationTimeResponseDto{" +
                "id=" + id +
                ", startAt='" + startAt + '\'' +
                '}';
    }
}
