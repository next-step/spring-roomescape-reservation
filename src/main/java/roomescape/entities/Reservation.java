package roomescape.entities;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDateTime reservedDateTime;
    private String date;
    private ReservationTime reservationTime;
    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }
    public Reservation(String name, String date, ReservationTime reservationTime) {
        this.name = name;
        this.date = date;
        this.reservationTime = reservationTime;
    }
    public Long getId() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public ReservationTime getReservationTime(){
        return this.reservationTime;
    }
    public String getDate(){
        return this.date;
    }
}
