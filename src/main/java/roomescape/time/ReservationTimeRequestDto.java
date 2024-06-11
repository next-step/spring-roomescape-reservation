package roomescape.time;

public class ReservationTimeRequestDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public ReservationTimeRequestDto(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private String startAt;

    public ReservationTimeRequestDto(String startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeRequestDto() {
    }

    public String getStartAt() {
        return startAt;
    }
}
