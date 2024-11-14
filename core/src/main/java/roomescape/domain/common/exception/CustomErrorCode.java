package roomescape.domain.common.exception;

import lombok.Getter;

@Getter
public enum CustomErrorCode {

    NOT_DEFINED("NOT DEFINED YET"),
    SERVER_ERROR("INTERNAL SERVER ERROR"),

    R404("Reservation not found"),
    R405("Reservation duplicated"),
    R406("Reservation already canceled"),

    RT404("ReservationTime not found"),
    RT405("ReservationTime duplicated"),
    RT406("ReservationTime already in use"),
    RT407("Reservation time out of range"),

    TH404("Theme not found"),
    TH406("Theme already in use")
    ;

    private final String description;

    CustomErrorCode(final String description) {
        this.description = description;
    }
}
