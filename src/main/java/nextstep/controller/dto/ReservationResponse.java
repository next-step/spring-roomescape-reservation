package nextstep.controller.dto;

public class ReservationResponse {

    private Integer id;
    private String date;
    private String time;
    private String name;

    private ReservationResponse() {
    }

    public ReservationResponse(Integer id, String date, String time, String name) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
