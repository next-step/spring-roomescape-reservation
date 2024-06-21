package roomescape.application.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import roomescape.application.service.command.CreateReservationCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateReservationRequest {

    private final static String DATETIME_FORMAT = "yyyy-MM-dd";

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "날짜는 yyyy-MM-dd 형식이어야 합니다.")
    private final String date;

    @NotBlank
    private final String name;

    @Positive
    @NotNull(message = "이 값은 필수입니다.")
    private final Long timeId;

    @Positive
    @NotNull(message = "이 값은 필수입니다.")
    private final Long themeId;


    public CreateReservationRequest(String date, String name, Long timeId, Long themeId) {
        this.date = date;
        this.name = name;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public CreateReservationCommand toCreateReservationCommand() {
        return new CreateReservationCommand(name, LocalDate.parse(date, DateTimeFormatter.ofPattern(DATETIME_FORMAT)), timeId, themeId);
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
