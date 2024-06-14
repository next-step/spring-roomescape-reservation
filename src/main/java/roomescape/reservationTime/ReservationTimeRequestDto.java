package roomescape.reservationTime;

public class ReservationTimeRequestDto {

    private Long id;
    private String startAt;

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    public ReservationTimeRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeRequestDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }
}
