package roomescape.entities;

import roomescape.reservation.data.ReservationAddRequestDTO;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(Long id, String name, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(ReservationAddRequestDTO reservationAddRequestDTO){
        this.name = reservationAddRequestDTO.getName();
        this.date = reservationAddRequestDTO.getDate();
        this.time = new ReservationTime(reservationAddRequestDTO.getReservationTimeId(), reservationAddRequestDTO.getStartAt());
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
