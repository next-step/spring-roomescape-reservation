package roomescape.application;

import roomescape.presentation.dto.ReservationRequest;

public class ReservationServiceInput {

    private final String name;
    private final String date;
    private final String time;

    private ReservationServiceInput(ReservationRequest request) {
        this.name = request.getName();
        this.date = request.getDate();
        this.time = request.getTime();
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

    public String getTime() {
        return time;
    }
}
