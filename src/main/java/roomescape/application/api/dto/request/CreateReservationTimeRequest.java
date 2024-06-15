package roomescape.application.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import roomescape.application.service.command.CreateReservationTimeCommand;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreateReservationTimeRequest {

    private static final String TIME_FORMAT = "HH:mm";

    @NotBlank
    @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "시작시간은 HH:mm 형식이어야 합니다.")
    private final String startAt;

    @JsonCreator
    public CreateReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public CreateReservationTimeCommand toCreateReservationTimeCommand() {
        return new CreateReservationTimeCommand(LocalTime.parse(this.startAt, DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    public String getStartAt() {
        return startAt;
    }
}
