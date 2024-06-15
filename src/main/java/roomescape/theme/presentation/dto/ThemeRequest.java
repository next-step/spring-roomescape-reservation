package roomescape.theme.presentation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ThemeRequest {

    private final String name;
    private final String description;
    private final String thumbnail;

    @JsonCreator
    public ThemeRequest(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("thumbnail") String thumbnail) {
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
