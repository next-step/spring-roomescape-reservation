package roomescape.reservation.data;

import roomescape.entities.ReservationTime;

public class ReservationAddRequestDto {
    private String name;
    private String date;
    private String time;

    public ReservationAddRequestDto() {
    }

    public ReservationAddRequestDto(String name, String date, ReservationTime reservationTime) {
        this.name = name;
        this.date = date;
        this.time = reservationTime.getTime();
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public boolean validateTime(){
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        if (hour >= 0 && hour <= 24 && minute >= 0 && minute <= 59){
            return true;
        }
        return false;
    }
}
