package roomescape.time.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import roomescape.time.domain.ReservationTime;

public record ReservationTimeCreateRequest(@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING)
                                           LocalTime startAt) {

    public ReservationTime toEntity() {
        return new ReservationTime(startAt);
    }
}
