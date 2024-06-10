package roomescape.presentation.dto;

import roomescape.application.ReservationTimeServiceOutput;

public class ReservationTimeResponse {

    private final long id;
    private final String startAt;

    private ReservationTimeResponse(ReservationTimeServiceOutput output) {
        this.id = output.getId();
        this.startAt = output.getStartAt();
    }

    public static ReservationTimeResponse createReservationTimeResponse(ReservationTimeServiceOutput output) {
        return new ReservationTimeResponse(output);
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
