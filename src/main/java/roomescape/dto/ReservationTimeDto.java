package roomescape.dto;

public class ReservationTimeDto {

    private Long id;

    private String startAt;

    public ReservationTimeDto(Long id, String startAt) {
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
