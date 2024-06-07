package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDto {

    private String name;
    private String date;
    private String time;

    public ReservationDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
