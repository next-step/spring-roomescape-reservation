package roomescape.reservation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NewReservation {
    @NotEmpty(message = "name can not be empty")
    private final String name;
    private final ReservationDate date;
    @NotNull(message = "timeId can not be null")
    private final Long timeId;

    public NewReservation(String name, ReservationDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public ReservationEntity toEntity() {
        return ReservationEntity.of(name, date.toString(), timeId);
    }
}
