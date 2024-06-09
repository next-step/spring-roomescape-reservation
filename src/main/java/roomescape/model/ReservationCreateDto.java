package roomescape.model;

public class ReservationCreateDto {
    private Long id;
    private String date;
    private String name;
    private Long timeId;

    public ReservationCreateDto() {
    }

    public ReservationCreateDto(Long id, String date, String name, Long timeId) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.timeId = timeId;
    }

    public Long getId() {
        return id;
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
