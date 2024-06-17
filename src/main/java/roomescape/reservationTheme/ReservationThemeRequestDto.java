package roomescape.reservationTheme;

import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class ReservationThemeRequestDto {
    @NotEmpty(message = "테마명을 입력 해주세요")
    private String name;

    @NotEmpty(message = "테마에 대한 설명을 입력해주세요")
    private String description;

    @NotEmpty(message = "썸네일 url 을 입력해주세요")
    private String thumbnail;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ReservationThemeRequestDto(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public ReservationThemeRequestDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationThemeRequestDto that = (ReservationThemeRequestDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "ReservationThemeRequestDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
