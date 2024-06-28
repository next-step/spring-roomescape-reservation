package roomescape.domain.reservationtime.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import roomescape.domain.reservationtime.model.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationTimeAppendHttpResponse {

    private final Long id;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;

    public ReservationTimeAppendHttpResponse(final Long id, final LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeAppendHttpResponse from(final ReservationTime time) {
        return new ReservationTimeAppendHttpResponse(
                time.getIdValue(),
                time.getStartAt()
        );
    }

    public static List<ReservationTimeAppendHttpResponse> from(final List<ReservationTime> times) {
        return times.stream()
                .map(ReservationTimeAppendHttpResponse::from)
                .toList();
    }
}
