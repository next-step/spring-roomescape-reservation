package roomescape.reservation;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "hh:mm";

    @Nullable
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate date;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime time;

    public Reservation(ReservationEntity entity) {
        this(entity.getId(), entity.getName(),
                LocalDate.parse(entity.getDate()),
                LocalTime.parse(entity.getTime()));
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(
                id,
                name,
                date.format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
                time.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
    }
}
