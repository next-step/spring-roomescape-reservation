package roomescape.application.api.dto.response;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;

import java.util.List;
import java.util.stream.Collectors;

public class FindReservationTimesResponse {

    private static final String TIME_PATTERN = "HH:mm";

    private final Long id;

    private final String startAt;

    public FindReservationTimesResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private static FindReservationTimesResponse from(ReservationTime reservationTime) {
        return new FindReservationTimesResponse(reservationTime.getId(), reservationTime.getFormattedStartAt(TIME_PATTERN));
    }

    public static List<FindReservationTimesResponse> toFindReservationResponses(ReservationTimes reservationTimes) {
        return reservationTimes.getReservationTimes().stream()
                .map(FindReservationTimesResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
