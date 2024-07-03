package roomescape.model;

public class ThemeCreateRequestDto {
    private String name;
    private String description;
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

    public ThemeCreateRequestDto() {
    }
}
