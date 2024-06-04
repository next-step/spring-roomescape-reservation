package roomescape.reservation.data;

import java.time.Instant;
import java.time.LocalDate;

public class ReservationAddRequestDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private Instant time;

    public ReservationAddRequestDTO(Long id, String name, LocalDate date, Instant time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
