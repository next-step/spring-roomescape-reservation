package nextstep.dto.reservation;

import nextstep.domain.Reservation;

public class ReservationFindResponse {
    private final Long id;
    private final String date;
    private final String time;
    private final String name;

    private ReservationFindResponse(Long id, String date, String time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public static ReservationFindResponse from(Reservation reservation) {
        return new ReservationFindResponse(
                reservation.getId(),
                reservation.getDate().toString(),
                reservation.getTime().toString(),
                reservation.getName()
        );
    }
}
