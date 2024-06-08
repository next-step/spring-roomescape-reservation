package roomescape.dto.response;

public class ReservationResponse {

    private Long id;
    private String name;
    private String date;
    private String time;

    public ReservationResponse(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationResponse() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
