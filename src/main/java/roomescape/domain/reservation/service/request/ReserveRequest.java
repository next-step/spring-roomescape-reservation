package roomescape.domain.reservation.service.request;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class ReserveRequest {

    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReserveRequest() {
    }

    @Builder
    private ReserveRequest(final String name, final LocalDate date, final LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public void validateAllFieldsExist() {
        if (Objects.isNull(this.name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(this.date)) {
            throw ReservationException.nullField("date");
        }
        if (Objects.isNull(this.time)) {
            throw ReservationException.nullField("time");
        }
    }
}
