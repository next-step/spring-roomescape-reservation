package roomescape.model;

public class ReservationCreateDto {

    private String date;
    private String name;
    private Long timeId;

    public ReservationCreateDto(String date, String name, Long timeId) {
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
