package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    private Long id;

    private String name;
    private String date;

    private Long timeId;

    private String startAt;


    public ReservationDto() {
    }

    public ReservationDto(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public ReservationDto(Long id, String name, String date, Long timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public ReservationDto(Long id, String name, String date, Long timeId, String startAt) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.startAt = startAt;
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

    public String getStartAt() {
        return startAt;
    }
}
