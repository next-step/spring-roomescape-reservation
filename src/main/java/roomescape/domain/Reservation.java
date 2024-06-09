package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;
    private ReservationTheme theme;

    public Reservation() {
    }

    public Reservation(Long id, String name, String date, ReservationTime time, ReservationTheme theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(String name, String date, ReservationTime time, ReservationTheme theme) {
        if (isDateExpired(date, time.getStartAt())) {
            throw new RuntimeException("이미 지나간 날짜는 예약할 수 없습니다.");
        }

        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    private boolean isDateExpired(String date, String startAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(startAt);

        if (now.isBefore(LocalDateTime.of(localDate, localTime))) {
            return true;
        }
        return false;
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

    public ReservationTime getTime() {
        return time;
    }

    public ReservationTheme getTheme() {
        return theme;
    }

    public Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id
                , reservation.getName()
                , reservation.getDate()
                , reservation.getTime()
                , reservation.getTheme());
    }
}
