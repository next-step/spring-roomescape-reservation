package roomescape.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReservationRq {
    @NotEmpty(message = "Reservation name must not be empty")
    private String name;

    @NotNull(message = "Reservation date must not be null")
    @Future(message = "Reservation date must be in the future")
    private LocalDate date;

    @NotNull(message = "Reservation time ID must not be null")
    private Long timeId;

    @NotNull(message = "Theme ID must not be null")
    private Long themeId;

    public ReservationRq() {}

    public ReservationRq(String name, LocalDate date, Long timeId, Long themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
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

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
