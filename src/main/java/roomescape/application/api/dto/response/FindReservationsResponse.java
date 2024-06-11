package roomescape.application.api.dto.response;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.Reservations;

import java.util.List;
import java.util.stream.Collectors;

public class FindReservationsResponse {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm";

    private final Long id;

    private final String name;

    private final String date;

    private final String time;

    private FindReservationsResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static FindReservationsResponse from(Reservation reservation) {
        return new FindReservationsResponse(
                reservation.getId(),
                reservation.getReservationName(),
                reservation.fetchReservationDateTime(DATE_PATTERN),
                reservation.fetchReservationDateTime(TIME_PATTERN)
        );
    }

    public static List<FindReservationsResponse> toFindReservationsResponses(Reservations reservations) {
        return reservations.fetchReservations()
                .stream()
                .map(FindReservationsResponse::from)
                .collect(Collectors.toList());
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

    public String getTime() {
        return time;
    }
}
