package roomescape.domain.reservation.service.request;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.exception.ReservationException;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ReserveRequest {

    private String name;
    private LocalDate date;
    private Long timeId;

    public ReserveRequest() {
    }

    @Builder
    private ReserveRequest(final String name, final LocalDate date, final Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public void validateAllFieldsExist() {
        if (Objects.isNull(this.name)) {
            throw ReservationException.nullField("name");
        }
        if (Objects.isNull(this.date)) {
            throw ReservationException.nullField("date");
        }
        if (Objects.isNull(this.timeId)) {
            throw ReservationException.nullField("time");
        }
    }
}
