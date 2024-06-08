package roomescape.presentation.dto;

import roomescape.application.ReservationServiceOutput;

public class ReservationResponse {

    private final long id;
    private final String name;
    private final String date;
    private final String time;

    private ReservationResponse(ReservationServiceOutput output) {
        this.id = output.getId();
        this.name = output.getName();
        this.date = output.getDate();
        this.time = output.getTime();
    }

    public static ReservationResponse createReservationResponse(ReservationServiceOutput output) {
        return new ReservationResponse(output);
    }

    public long getId() {
        return id;
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
