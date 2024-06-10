package roomescape.dto.reservation.create;

import roomescape.dto.time.ReservationTimeResponse;

import java.time.LocalDate;

public class ReservationCreateRequest {

    private String date;
    private String name;
    private Long timeId;

    public ReservationCreateResponse toResponse(Long id, String date, String name, ReservationTimeResponse time) {
        return new ReservationCreateResponse(id, LocalDate.parse(date), name, time);
    }

    public ReservationCreateRequest() {
    }

    public ReservationCreateRequest(String date, String name, Long timeId) {
        this.date = date;
        this.name = name;
        this.timeId = timeId;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }

}
