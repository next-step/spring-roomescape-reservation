package roomescape.DTO;

import roomescape.Entity.Reservation;

import java.util.List;

public class ReservationResponse {
    private final Long id;
    private final String name;
    private final String date;
    private final ReservationTimeResponse time;

    public ReservationResponse(Long id, String name, String date, ReservationTimeResponse time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = new ReservationTimeResponse(reservation.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public ReservationTimeResponse getTime() {
        return time;
    }

    public static List<ReservationResponse> toDTOList(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::new).toList();
    }
}
