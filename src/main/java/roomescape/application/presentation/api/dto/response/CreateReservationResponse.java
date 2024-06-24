package roomescape.application.presentation.api.dto.response;

import roomescape.domain.reservation.Reservation;

public class CreateReservationResponse {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private final Long id;

    private final String name;

    private final String date;

    private final Long timeId;

    public CreateReservationResponse(Long id, String name, String date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static CreateReservationResponse from(Reservation reservation) {
        return new CreateReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getFormattedReservationDate(DATE_PATTERN),
                reservation.getReservationTimeId()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}
