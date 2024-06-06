package roomescape.dto;

public class ReservationRequest {
    private Long id;
    private String name;
    private String date;
    private String timeId;

    public ReservationRequest() {
    }

    public ReservationRequest(Long id, String name, String date, String timeId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public ReservationRequest(String name, String date, String timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
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

    public String getTimeId() {
        return timeId;
    }
}
