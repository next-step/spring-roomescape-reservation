package roomescape.entities;

import roomescape.errors.ErrorCode;
import roomescape.exceptions.SpringRoomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime reservationTime;
    public Reservation(Long id, String name, String date, ReservationTime reservationTime) {
        if (!isValidReservedDateTime(date, reservationTime)){
            throw new SpringRoomException(ErrorCode.INVALID_INPUT_VALUE, "예약 날짜가 현재 시간보다 이전입니다.");
        }
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

    public boolean isValidReservedDateTime(String date, ReservationTime reservationTime){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservedDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(reservationTime.getTime()));
        return now.isBefore(reservedDateTime);
    }
}
