package roomescape.reservation.data;

import java.time.Instant;
import java.time.LocalDate;

public class ReservationSearchResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private Instant time;

    public ReservationSearchResponse(Long id, String name, LocalDate date, Instant time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
