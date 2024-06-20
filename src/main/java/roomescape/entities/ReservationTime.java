package roomescape.entities;

import roomescape.reservationtime.data.ReservationTimeAddRequestDto;

public class ReservationTime {
    private Long id;
    private String time;

    public ReservationTime(ReservationTimeAddRequestDto reservationTimeAddRequestDTO) {
        if (!isValidTime(reservationTimeAddRequestDTO.getTime())){
            throw new IllegalArgumentException();
        }
        this.time = reservationTimeAddRequestDTO.getTime();
    }

    public ReservationTime(String time){
        this.time = time;
    }

    public ReservationTime(Long reservationTimeId, String time) {
        this.id = reservationTimeId;
        this.time = time;
    }

    public Long getId() {
        return this.id;
    }

    public String getTime() {
        return this.time;
    }

    private boolean isValidTime(String time){
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        if (hour >= 0 && hour <= 24 && minute >= 0 && minute <= 59){
            return true;
        }
        return false;
    }
}
