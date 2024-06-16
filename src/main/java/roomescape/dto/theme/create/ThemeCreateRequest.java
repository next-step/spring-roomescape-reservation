package roomescape.dto.theme.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ThemeCreateRequest {

    @NotBlank(message = "테마의 제목은 필수입니다.")
    @Size(min = 3, max = 20, message = "제목은 3글자 이상 20글자 이하여야합니다.")
    private String name;
    @NotBlank(message = "테마의 설명은 필수입니다.")
    @Size(min = 5, message = "설명이 너무 짧습니다. 더 작성해주세요.")
    private String description;
    private String thumbnail;

    public ThemeCreateRequest() {
    }

    public ThemeCreateRequest(String name, String description, String thumbnail) {
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
