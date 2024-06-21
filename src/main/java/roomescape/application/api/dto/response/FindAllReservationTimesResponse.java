package roomescape.application.api.dto.response;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimes;

import java.util.List;
import java.util.stream.Collectors;

public class FindAllReservationTimesResponse {

    private static final String TIME_PATTERN = "HH:mm";

    private final Long id;

    private final String startAt;

    public FindAllReservationTimesResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    private static FindAllReservationTimesResponse from(ReservationTime reservationTime) {
        return new FindAllReservationTimesResponse(reservationTime.getId(), reservationTime.getFormattedStartAt(TIME_PATTERN));
    }

    public static List<FindAllReservationTimesResponse> toFindAllReservationResponses(ReservationTimes reservationTimes) {
        return reservationTimes.getReservationTimes().stream()
                .map(FindAllReservationTimesResponse::from)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
