package roomescape.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ReservationThemeRequest {

    @NotBlank(message = "테마이름은 필수 값입니다.")
    private String name;

    @NotBlank(message = "테마설명은 필수 값입니다.")
    private String description;

    @NotBlank(message = "테마이미지는 필수 값입니다.")
    private String thumbnail;

    public ReservationThemeRequest() {
    }

    public ReservationThemeRequest(String name, String description, String thumbnail) {
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
