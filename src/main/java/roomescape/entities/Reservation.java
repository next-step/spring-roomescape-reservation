package roomescape.entities;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(String name, String date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public ReservationTime getTime(){
        return this.time;
    }

    public String getDate(){
        return this.date;
    }


}
