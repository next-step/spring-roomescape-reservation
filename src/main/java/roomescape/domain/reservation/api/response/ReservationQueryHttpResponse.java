package roomescape.domain.reservation.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.service.response.ReservationQueryResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationQueryHttpResponse {

    private final Long id;

    private final String name;

    private final LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private final LocalTime time;

    @Builder
    private ReservationQueryHttpResponse(
            final Long id,
            final String name,
            final LocalDate date,
            final LocalTime time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static List<ReservationQueryHttpResponse> from(final List<ReservationQueryResponse> reservations) {
        return reservations.stream()
                .map(ReservationQueryHttpResponse::from)
                .toList();
    }

    public static ReservationQueryHttpResponse from(final ReservationQueryResponse response) {
        return ReservationQueryHttpResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .date(response.getDate())
                .time(response.getTime())
                .build();
    }

}
