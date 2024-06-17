package roomescape.reservationTime;

import jakarta.validation.constraints.NotBlank;

public class ReservationTimeRequestDto {

    private Long id;
    @NotBlank(message = "예약 시간을 입력해주세요")
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
