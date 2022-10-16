package nextstep.web.endpoint.reservation.response;

import lombok.Getter;
import lombok.ToString;
import nextstep.domain.reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ReservationResponse {
    private final Long id;
    private final LocalDate date;
    private final LocalTime time;
    private final String name;

    private ReservationResponse(Long id, LocalDate date, LocalTime time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public static List<ReservationResponse> fromList(List<Reservation> reservations) {
        return reservations.stream().map(it ->
                new ReservationResponse(
                        it.getId(),
                        it.getDate(),
                        it.getTime(),
                        it.getName()
                )
        ).collect(Collectors.toList());
    }
}
