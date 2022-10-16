package nextstep.dto;

import nextstep.domain.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationFindAllResponse {
    private final List<ReservationFindResponse> reservations;

    private ReservationFindAllResponse(List<ReservationFindResponse> reservations) {
        this.reservations = reservations;
    }

    public List<ReservationFindResponse> getReservations() {
        return reservations;
    }

    public static ReservationFindAllResponse from(List<Reservation> reservations) {
        List<ReservationFindResponse> reservationFindResponses = reservations.stream()
                .map(ReservationFindResponse::from)
                .collect(Collectors.toList());
        return new ReservationFindAllResponse(reservationFindResponses);
    }
}
