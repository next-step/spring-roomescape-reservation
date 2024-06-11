package roomescape.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {
    private static final String TIME_PATTERN = "hh:mm";

    @Nullable
    private Long id;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_PATTERN)
    private LocalTime startAt;

    public ReservationTime(ReservationTimeEntity entity) {
        this(entity.getId(), LocalTime.parse(entity.getStartAt()));
    }

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTimeEntity toEntity() {
        return new ReservationTimeEntity(
                id,
                startAt.format(DateTimeFormatter.ofPattern(TIME_PATTERN))
        );
    }
}
