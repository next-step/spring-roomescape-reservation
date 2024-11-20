package roomescape.domain.theme.api.request;

import lombok.Getter;
import roomescape.domain.theme.application.request.ThemeAppendRequest;

@Getter
public class ThemeAppendHttpRequest {

    private String name;
    private String description;
    private String thumbnail;

    public ThemeAppendHttpRequest() {
    }

    public ThemeAppendRequest toServiceRequest() {
        return ThemeAppendRequest.builder()
                .name(this.name)
                .description(this.description)
                .thumbnail(this.thumbnail)
                .build();
    }
}
