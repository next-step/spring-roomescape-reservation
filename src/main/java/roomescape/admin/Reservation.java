package roomescape.admin;

public class Reservation {


    private Long id;
    private String name;
    private String date;
    private String time;

    public static Reservation add(SaveReservationRequest saveReservationRequest){
        return new Reservation(saveReservationRequest);
    }

    private Reservation(SaveReservationRequest saveReservationRequest) {
        this.name = saveReservationRequest.name();
        this.date = saveReservationRequest.date();
        this.time = saveReservationRequest.time();
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

    public String getTime() {
        return time;
    }

}