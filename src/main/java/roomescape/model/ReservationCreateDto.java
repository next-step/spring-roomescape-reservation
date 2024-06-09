package roomescape.model;

public class ReservationCreateDto {

    private String date;
    private String name;
    private String time;

    public ReservationCreateDto() {
    }

    public ReservationCreateDto(String date, String name, String time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
