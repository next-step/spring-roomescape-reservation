package roomescape.application;

import roomescape.presentation.dto.ReservationTimeRequest;

public class ReservationTimeServiceInput {

    private final String startAt;

    private ReservationTimeServiceInput(ReservationTimeRequest request) {
        this.startAt = request.getStartAt();
    }

    public static ReservationTimeServiceInput createReservationTimeServiceInput(ReservationTimeRequest request) {
        return new ReservationTimeServiceInput(request);
    }

    public String getStartAt() {
        return startAt;
    }
}
