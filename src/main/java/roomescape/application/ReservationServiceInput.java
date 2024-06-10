package roomescape.application;

import roomescape.presentation.dto.ReservationRequest;

public class ReservationServiceInput {

    private final String name;
    private final String date;
    private final long timeId;
    private final String startAt;

    private ReservationServiceInput(ReservationRequest request) {
        this.name = request.getName();
        this.date = request.getDate();
        this.timeId = request.getTimeId();
        this.startAt = request.getStartAt();
    }

    public static ReservationServiceInput createReservationServiceInput(ReservationRequest request) {
        return new ReservationServiceInput(request);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }

    public String getStartAt() {
        return startAt;
    }
}
