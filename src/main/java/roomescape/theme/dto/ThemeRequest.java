package roomescape.theme.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.error.RoomescapeErrorMessage;

public class ThemeRequest {

    @NotBlank(message = "테마 이름" + RoomescapeErrorMessage.BLANK_INPUT_VALUE_EXCEPTION)
    private String name;

    @NotBlank(message = "테마 설명" + RoomescapeErrorMessage.BLANK_INPUT_VALUE_EXCEPTION)
    private String description;

    @NotBlank(message = "테마 썸네일 URL" + RoomescapeErrorMessage.BLANK_INPUT_VALUE_EXCEPTION)
    private String thumbnail;

    public ThemeRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
