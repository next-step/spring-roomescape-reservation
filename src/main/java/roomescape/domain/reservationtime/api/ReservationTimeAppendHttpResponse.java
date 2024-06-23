package roomescape.domain.reservationtime.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import roomescape.domain.reservationtime.model.ReservationTime;

import java.time.LocalTime;

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
}
